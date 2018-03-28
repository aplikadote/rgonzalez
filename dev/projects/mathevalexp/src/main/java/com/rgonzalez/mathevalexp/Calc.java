/*
 * Calc.java
 *
 * Created on Mayo 2, 2007, 9:38 PM
 * @author Luis Chinchilla
 *
 */

package com.rgonzalez.mathevalexp;

public class Calc {
    
    public Calc() { }
    
    public double calc(String expresion) throws Exception {  
      Program program = new Program(expresion);
      Scanner scanner = new Scanner();
      Parser parser = new Parser();
      Interpreter interpreter = new Interpreter();
      program = scanner.scan(program);
      program = parser.parse(program);
      double result = interpreter.interpret(program);

      return result;
   }
}
