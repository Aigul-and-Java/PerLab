public class task2 {
    public static void main(String[] args) throws Exception {

        int centerX = Integer.parseInt(args[0]);
        int centerY = Integer.parseInt(args[1]);
        int radius = Integer.parseInt(args[2]);


        int pointX = Integer.parseInt(args[3]);
        int pointY = Integer.parseInt(args[4]);
        int distance = (pointX - centerX) * (pointX - centerX) + (pointY - centerY) * (pointY - centerY);

        if (distance == radius * radius) {
            System.out.println(0); // точка лежит на окружности
        } else if (distance < radius * radius) {
            System.out.println(1); // точка внутри окружности
        } else {
            System.out.println(2); // точка снаружи окружности
        }
    }
}
