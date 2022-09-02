package module;

public class Main {
  public static void main(String[] args) {
    String result = module.Generated.getFoo();
    String expected = "bar";
    if(result != expected) throw new AssertionError();
  }
}
