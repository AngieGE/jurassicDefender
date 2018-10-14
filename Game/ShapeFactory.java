//Hay que importar el paquete

public class ShapeFactory implements Factory{

  private static class SingletonHolder{
    private static ShapeFactory instance = new ShapeFactory();
  }

  public static ShapeFactory getInstance(){
    return SingletonHolder.instance;
  }

  public Shape createShape(int tipo){
    Shape shape = null;
      switch(tipo){
        case 1:
          //return new Bullets();
          Bullets b = new Bullets(false);
          shape = (Shape) b;
          break;
        case 2:
          Meteorito m = new Meteorito();
          shape = (Shape) m;
          break;
          //return new Meteorito();
        case 3:
          Dinosaur d = new Dinosaur();
          shape = (Shape) d;
          break;
          //return new Dinosaur();
        case 4:
          //return new Circle();
          Circle c = new Circle();
          shape = (Shape) c;
          break;
        case 5:
          //return new Bullets();
          Bullets bc = new Bullets(true);
          shape = (Shape) bc;
          break;
        default: 
          System.out.println("Error al crear el objeto");
          return null;
      }
      return shape;
    }
}
