package kr.ac.kopo.ctc.kopo28.board.web;

import kr.ac.kopo.ctc.kopo28.board.domain.Board;
import kr.ac.kopo.ctc.kopo28.board.domain.Comment;
import kr.ac.kopo.ctc.kopo28.board.domain.Post;
import kr.ac.kopo.ctc.kopo28.board.service.CommentService;
import kr.ac.kopo.ctc.kopo28.board.service.PostService;
import kr.ac.kopo.ctc.kopo28.board.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import kr.ac.kopo.ctc.kopo28.board.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List; // Keep this for comments list
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class PostController {

    private static final Logger log = LoggerFactory.getLogger(PostController.class);

    private final PostService postService;
    private final CommentService commentService;
    private final UserRepository userRepository;

    @Autowired
    public PostController(PostService postService, CommentService commentService, UserRepository userRepository) {
        this.postService = postService;
        this.commentService = commentService;
        this.userRepository = userRepository;
    }

    // 게시판 목록 조회
    @GetMapping("/board/{boardId}")
    public String getPostList(@PathVariable Long boardId,
                              @PageableDefault(size = 15, sort = "post_id", direction = Sort.Direction.DESC) Pageable pageable,
                              Model model) {
        Page<Post> postPage = postService.findAllByBoardId(boardId, pageable);
        model.addAttribute("postPage", postPage);
        model.addAttribute("boardId", boardId);
        return "board/list";
    }

    // 게시글 상세 조회
    @GetMapping("/post/{postId}")
    public String getPostView(@PathVariable Long postId,
                              @RequestParam(value = "commentPage", defaultValue = "0") int commentPage,
                              @RequestParam(value = "commentSize", defaultValue = "10") int commentSize,
                              Model model) {
        Post post = postService.findById(postId);
        // Use getPageByPostId from CommentService
        Page<Comment> commentsPage = commentService.getPageByPostId(postId, commentPage, commentSize);
        model.addAttribute("post", post);
        model.addAttribute("commentsPage", commentsPage); // Pass Page object
        model.addAttribute("newComment", new Comment()); // For the new comment form
        return "board/view";
    }

    // 새 게시글 작성 폼
    @GetMapping("/board/write")
    public String getNewPostForm(@RequestParam("boardId") Long boardId, Model model) {
        Post post = new Post();
        Board board = new Board();
        board.setBoard_id(boardId);
        post.setBoard(board);
        model.addAttribute("post", post);
        model.addAttribute("boardId", boardId);
        return "board/form";
    }

    // 새 게시글 저장
    @PostMapping("/board/writepro")
    public String createNewPost(@ModelAttribute Post post, @RequestParam("boardId") Long boardId) {
        // Manually set the board
        Board board = new Board();
        board.setBoard_id(boardId);
        post.setBoard(board);

        // Get authenticated user and set as author
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof User) {
            User currentUser = (User) authentication.getPrincipal();
            post.setUser(currentUser); // Set the authenticated user as the post author
        } else {
            // Handle case where user is not authenticated or principal is not a User object
            // This should ideally not happen if security is configured correctly for this endpoint
            return "redirect:/error?message=User not authenticated or invalid principal";
        }

        Post savedPost = postService.save(post);
        return "redirect:/post/" + savedPost.getPost_id();
    }

    // 게시글 수정 폼
    @GetMapping("/post/edit/{postId}")
    public String getEditPostForm(@PathVariable Long postId, Model model) {
        Post post = postService.findById(postId);
        model.addAttribute("post", post);
        return "board/form";
    }

    // 게시글 수정
    @PostMapping("/post/edit/{postId}")
    public String updatePost(@PathVariable Long postId, @ModelAttribute Post postDetails) {
        Post existingPost = postService.findById(postId);
        if (existingPost == null) {
            return "redirect:/error?message=Post not found";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
            return "redirect:/error?message=Unauthorized"; // Not logged in or anonymous user
        }

        User currentUser = (User) authentication.getPrincipal();

        // Check if current user is admin or the author of the post
        if (currentUser.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) || currentUser.getUser_id().equals(existingPost.getUser().getUser_id())) {
            postService.update(postId, postDetails);
            return "redirect:/post/" + postId;
        } else {
            return "redirect:/error?message=Access Denied"; // Not authorized
        }
    }

    // 게시글 삭제
    @PostMapping("/post/delete/{postId}")
    public String deletePost(@PathVariable Long postId) {
        Post post = postService.findById(postId);
        if (post == null) {
            return "redirect:/error?message=Post not found";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
            return "redirect:/error?message=Unauthorized"; // Not logged in or anonymous user
        }

        User currentUser = (User) authentication.getPrincipal();
        log.info("Current user authorities (deletePost): {}", currentUser.getAuthorities());
        if (currentUser.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) || currentUser.getUser_id().equals(post.getUser().getUser_id())) {
            Long boardId = post.getBoard().getBoard_id();
            postService.deleteById(postId);
            return "redirect:/board/" + boardId;
        } else {
            return "redirect:/error?message=Access Denied"; // Not authorized
        }
    }

    // 게시글 추천
    @PostMapping("/post/recommend/{postId}")
    public String recommendPost(@PathVariable Long postId) {
        postService.recommend(postId);
        return "redirect:/post/" + postId;
    }

    // 댓글 작성
    @PostMapping("/post/{postId}/comment")
    public String addComment(@PathVariable Long postId,
                             @ModelAttribute Comment comment,
                             @RequestParam(value = "parentId", required = false) Long parentId) {
        log.info("Attempting to add comment for postId: {}, content: {}, parentId: {}", postId, comment.getContent(), parentId);
        // TODO: Set user properly from security context
        // For now, assuming user is handled by service or dummy
        Long dummyUserId = 1L; // Using dummy user ID for now
        commentService.save(comment.getContent(), postId, parentId, dummyUserId); // Use comment.getContent()
        return "redirect:/post/" + postId;
    }

    // 게시글 검색
    @GetMapping("/post/search")
    public String searchPosts(@RequestParam(value = "keyword", required = false) String keyword,
                              @PageableDefault(size = 15, sort = "post_id", direction = Sort.Direction.DESC) Pageable pageable,
                              Model model) {
        Page<Post> postPage;
        if (keyword != null && !keyword.trim().isEmpty()) {
            postPage = postService.searchPosts(keyword.trim(), pageable);
        } else {
            // If no keyword, return an empty page or all posts (depending on desired behavior)
            // For now, let's return an empty page if no keyword is provided for search
            postPage = Page.empty(pageable);
        }
        model.addAttribute("postPage", postPage);
        model.addAttribute("keyword", keyword); // Pass keyword back to the view for display
        return "board/list"; // Reuse the list.jsp to display search results
    }

    // 댓글 삭제
    @PostMapping("/comment/delete/{commentId}")
    public String deleteComment(@PathVariable Long commentId) {
        // TODO: Add authorization check
        // This is a simplified version. A better approach would be to pass the postId
        // back to redirect to the correct page.
        commentService.deleteById(commentId);
        return "redirect:/"; // Redirect to home
    }
}