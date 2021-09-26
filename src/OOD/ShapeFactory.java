package OOD;

interface Shape{
    public void draw(int size);
}

class Rectangle implements Shape{
    public Rectangle(){
    }

    @Override
    public void draw(int size){
        System.out.println("Inside Rectangle::draw() method.");
    }
}

class Circle implements Shape{
    public Circle(){
    }

    @Override
    public void draw(int size){
        System.out.println("Inside Circle::draw() method.");
    }
}

class Triangle implements Shape{
    public Triangle(){
    }

    @Override
    public void draw(int size){
        System.out.println("Inside Triangle::draw() method.");
    }
}

public class ShapeFactory {

    public Shape getShape(String shape){
        if (shape == null){
            return null;
        }
        if (shape.equalsIgnoreCase("CIRCLE")){
            return new Circle();
        }else if (shape.equalsIgnoreCase("RECTANGLE")){
            return new Rectangle();
        }else if (shape.equalsIgnoreCase("TRIANGLE")){
            return new Triangle();
        }
        return null;
    }
}


class FactoryDemo{
    private static final ShapeFactory shapeFactory = new ShapeFactory();

    public static void main(String[] args){
        Shape shape1 = shapeFactory.getShape("Circle");
        shape1.draw(6);

        Shape shape2 = shapeFactory.getShape("Triangle");
        shape2.draw(10);

        Shape shape3 = shapeFactory.getShape("Rectangle");
        shape3.draw(8);
    }
}