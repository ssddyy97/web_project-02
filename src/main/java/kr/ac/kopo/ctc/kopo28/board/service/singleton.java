package kr.ac.kopo.ctc.kopo28.board.service;




class Singleton {
        private static Singleton instance;

        private Singleton() {
            // private 생성자: 외부에서 new로 인스턴스를 만들 수 없음
        }

        public static Singleton getInstance() {
            if (instance == null) {
                instance = new Singleton();
            }
            return instance;
        }
}

