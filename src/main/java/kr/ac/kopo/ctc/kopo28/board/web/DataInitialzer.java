package kr.ac.kopo.ctc.kopo28.board.web;


import kr.ac.kopo.ctc.kopo28.board.domain.Board;
import kr.ac.kopo.ctc.kopo28.board.domain.Post;
import kr.ac.kopo.ctc.kopo28.board.domain.User;
import kr.ac.kopo.ctc.kopo28.board.repository.BoardRepository;
import kr.ac.kopo.ctc.kopo28.board.repository.PostRepository;
import kr.ac.kopo.ctc.kopo28.board.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@Configuration
public class DataInitialzer {

    @Bean
    public CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder, PostRepository postRepository, BoardRepository boardRepository) {
        return args -> {
            User adminUser = null;
            if (userRepository.findByName("admin").isEmpty()) {
                var admin = new kr.ac.kopo.ctc.kopo28.board.domain.User();
                admin.setName("admin");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setRole("ADMIN,USER"); // ADMIN 권한 포함
                adminUser = userRepository.save(admin);
                System.out.println("Admin user created: admin / admin");
            } else {
                adminUser = userRepository.findByName("admin").get();
            }

            if (userRepository.findByName("user").isEmpty()) {
                var user = new kr.ac.kopo.ctc.kopo28.board.domain.User();
                user.setName("user");
                user.setPassword(passwordEncoder.encode("user"));
                user.setRole("USER");
                userRepository.saveAndFlush(user);
                System.out.println("Regular user created: user / user");
            }

            // Create a default board if it doesn't exist
            Board defaultBoard = boardRepository.findById(1L).orElseGet(() -> {
                Board board = new Board();
                board.setName("자유게시판");
                return boardRepository.save(board);
            });

            // Generate 1000 dummy posts if no posts exist
            if (postRepository.count() == 0) {
                User finalAdminUser = adminUser;
                IntStream.range(0, 1000).forEach(i -> {
                    Post post = new Post();
                    post.setTitle("더미 게시글 " + (i + 1));
                    post.setBody("이것은 " + (i + 1) + "번째 더미 게시글의 내용입니다.");
                    post.setCreatedAt(LocalDateTime.now());
                    post.setUser(finalAdminUser);
                    post.setBoard(defaultBoard);
                    postRepository.save(post);
                });
                System.out.println("Generated 1000 dummy posts.");
            }
        };
    }



}
