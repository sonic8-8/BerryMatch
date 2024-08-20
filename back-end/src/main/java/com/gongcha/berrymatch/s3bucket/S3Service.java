package com.gongcha.berrymatch.s3bucket;

public class S3Service {

    // 바이요를 출력하는 메서드
    public void _1번() {
        System.out.println("바이요");
    }

    /*
    헤이요를 출력하는 메서드
     */
    public void _2번() {
        System.out.println("헤이요");
    }

    /**
     * 하이요를 출력하는 메서드
     */
    public void _3번() {
        System.out.println("하이요");
    }

    public static void main(String[] args) {

        S3Service service = new S3Service();

        service._1번();

        service._2번();

        service._3번();
    }

}
