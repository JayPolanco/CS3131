import java.util.Stack;

/*Project Goal: Take a mathimatical expression and convert it 
 * to prefix, postfix and fully parenthesised notation using a Binary
 * tree and a stack*/

public class ExpressionProject extends ExpressionTree {

   public static void main(String args[]) {
      /*Replace the expression below to test a different expression when executed.
      Then you may enter a mathematical expression on the keyboard which
      will also get made into a binary tree. It will then be printed in post/in-fix notation
      as well as fully parenthesized.
      */
      ExpressionProject y = new ExpressionProject("4 + (ax / 1) - x*2 ");
      Utility.print(y);
      y = new ExpressionProject(Utility.getInput());
      Utility.print(y);
   }
   
   //returns the fully parenthesized answer
   public String fullyParenthesised() {
      String ans = "";
      ans = inOrderParens((BNode<String>) root, ans); 
      return ans;
   }
   
   //adds parentheses accordingly and returns the answer as a string
   private String inOrderParens(BNode<String> p, String ans) {
      /*if node p is an operation inOrderParens its left and right child,
      else just return info in node*/
      if(p.getData() == "*" || p.getData() == "/" || p.getData() == "+" || p.getData() == "-"){
         ans += "(";   
         ans = inOrderParens(p.getLeft(), ans);
         ans += p.getData();
         ans = inOrderParens(p.getRight(), ans);
         ans += ")";
      }
      else{ 
         ans += p.getData();}
      return ans;
}

   public ExpressionProject(String s) {
      super();
      String expr = s.replaceAll("\\s+","");   //removes blanks in strings
      Stack<String> st = new Stack<String>(); //stack to keep track of depth
      root = makeTree(expr, st); //returns the root node to the tree
   }
   //returns a BNode with the root in it
   private BNode<String> makeTree(String expr, Stack<String> st) {
      String r = null; //root of sub trees
      int indexOfRoot = 0; // location of root in string
      BNode<String> operation; //the root as a node
      
      //if node is a # or string/variable then return that #/variable
      if(expr.matches("^[a-zA-Z_0-9]\\.?[a-zA-Z_0-9]*$")){ 
         r = expr;
         return operation = new BNode<String>(r, null, null,null);
      }   
      else{  //finds the location of operator and the index of it
         for(int i = 0; i < expr.length(); i++){
            if(st.size() == 0){  //if the depth is zero
               if(expr.charAt(i) == '+'){
                  r = "+";
                  indexOfRoot = i; }
               else if(expr.charAt(i) == '-'){
                  r = "-";
                  indexOfRoot = i; }
               else if(expr.charAt(i) == '*' && !(r == "+" || r == "-")){
                  r = "*";
                  indexOfRoot = i; }
               else if(expr.charAt(i) == '/' && !(r == "+" || r == "-")){
                  r = "/";
                  indexOfRoot = i;}
               else if(expr.charAt(i) == '(')  //add ( to the stack, increase depth
                  st.push(Character.toString(expr.charAt(i)));
               
            }
            else if(expr.charAt(i) == ')')
               st.pop(); //decrease depth when we get a closing parens
            else{
               continue;
            }
      }
      operation = new BNode<String>(r, null, null,null);
      String leftChild = ignoreParens(expr.substring(0, indexOfRoot));
      String rightChild = ignoreParens(expr.substring(indexOfRoot + 1, expr.length()));
      operation.setLeft(makeTree(leftChild, st)); //sets left child of root to the next subTree
      operation.setRight(makeTree(rightChild, st)); //same for right child
      
      return operation; //return the node with the operation
      }
   }

   //returns the string without parens if in correct form
   private String ignoreParens(String s) {
      if((s.charAt(0) == '(') && (s.charAt(s.length() - 1) == ')'))
         return s.substring(1, s.length() - 1 );
      else   
         return s;
   }
}
