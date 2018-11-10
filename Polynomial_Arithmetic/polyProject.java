/*Project goal: Take in 2 polynomials and print out the addition, subtraction,
  product and quotient of the polynomials using a linked list */

public class polyProject extends Polynomial {
   public static void main(String args[]) throws Exception {
      Polynomial p = new polyProject("X^5"), 
            q = new polyProject("X^2 -X + 1");
      Utility.run(p, q);
   }
   
   public polyProject(String s) {
      //Initialize to 1 because very monomial has at least a coefficient of 1
      Term term;
      Double coef = 1.0; 
      int degree;

      //replaces all - to +-, ex:-X^5 turns to +-X^5
      String rString = s.replace("-", "+-");
      
      //Replaces spaces with no spaces
      rString = rString.replaceAll(" ", "");
      //split replaced stings by + symbol
      String[] mon = rString.split("\\+");  //need to put \\ to make it a character and not a 1 or more regex rule
      
      //goes through every monmial and splits it again by ^
      for(int i = 0; i < mon.length; i++){
      //holds the useless first term we get if a negative is in front
         String uselessFirst;
         uselessFirst = " " + mon[i];
            
         //if the first term is empty continue to the next element(actual first term)
         if(uselessFirst.equals(" ")) continue; 
            //if the first character is a minus sign add space char
         if(mon[i].charAt(0) == '-') mon[i] =" " + mon[i];
            
            //If the first character is a space, add space char to mon[i], only happens when its a +#
         else if(uselessFirst.charAt(0) == ' ') mon[i] = " " + mon[i];
         String[] split2 = mon[i].split("\\^");
            
         //location of the X in piece
         int xLocation = split2[0].length();
         //if the string does not contain an X then its a coefficient
         if(!split2[0].contains("X")){
            coef = Integer.parseInt(split2[0].substring(1,xLocation))/1.0;
            term = new Term(coef, 0);
            data.addLast(term);
            continue;
         }
         if(split2[0].charAt(0) == ' ' && split2[0].charAt(1) == '-' && !(Character.isDigit(split2[0].charAt(2))))
            coef = -1.0;
         else if(split2[0].charAt(0) == ' ' && split2[0].charAt(1) == '-' && split2[0].charAt(2) == 'X')
            coef = -1.0;
         else if(split2[0].charAt(0) == ' ' && split2[0].charAt(1) == 'X')
            coef = 1.0;
         else
            coef = Integer.parseInt(split2[0].substring(1,xLocation-1))/1.0;
               
            //if there is no exponent then split2 will be of size 1; if there is an exponent the length will be more then one
            //length is the size of the array split2, if we have an exponent split2.length will be size 2
         if(split2.length == 1) degree = 1;  //Location of degree number 
         else{
            xLocation = split2[1].length();
            degree = Integer.parseInt(split2[1].substring(0, xLocation));
         }
            term = new Term(coef, degree);
            data.addLast(term);
         }         
   }   
   public polyProject() {
      super();
   }

   public Polynomial add(Polynomial p) {
      Polynomial ans = new polyProject();
      Term term;
      DNode<Term> in1 = null,  in2 = null;
      try {
         in1 = this.data.getFirst(); in2 = p.data.getFirst();
      } catch (Exception e) {
         e.printStackTrace();
      } //while both list haven't finished
      while(in1.getData() != null || in2.getData() != null){
         if(in1.getData() == null){ //if 1st list is empty then add the rest of the terms of list 2
            term = new Term(in2.getData().coefficient, in2.getData().degree);
            ans.data.addLast(term);
            in2 = in2.getNext();
            continue;
         }
         if(in2.getData() == null){ //if 2nd list is empty then add the rest of the terms of list 1
            term = new Term(in1.getData().coefficient, in1.getData().degree);
            ans.data.addLast(term);
            in1 = in1.getNext();
            continue;
         }
         //if both terms have the same degree add the terms
         if(in1.getData().degree == in2.getData().degree){
            term = new Term((in1.getData().coefficient) + (in2.getData().coefficient), in1.getData().degree);
            //if coefficient doesn't add to 0 then add addition of terms to list otherwise move to the next terms
            if(term.coefficient != 0.0){
               ans.data.addLast(term);
            }
            in1 = in1.getNext();
            in2 = in2.getNext();
         }
         //if term in 1st list has a higher power than the term in 2, add 1st to ans list
         else if(in1.getData().degree > in2.getData().degree){
            term = new Term(in1.getData().coefficient, in1.getData().degree);
            ans.data.addLast(term);
            in1 = in1.getNext();   
         }
         //if term in 2nd list has a higher power than the term in 1, add 2nd to ans list
         else if(in1.getData().degree < in2.getData().degree){
            term = new Term(in2.getData().coefficient, in2.getData().degree);
            ans.data.addLast(term);
            in2 = in2.getNext();
         }
         
      }
      return ans;
   }
   //Same as add method, more or less 
   public Polynomial subtract(Polynomial p) {
      Polynomial ans = new polyProject();
      Term term;
      DNode<Term> in1 = null,  in2 = null;
      try {
         in1 = this.data.getFirst(); in2 = p.data.getFirst();
      } catch (Exception e) {
         e.printStackTrace();
      }
      while(in1.getData() != null || in2.getData() != null){
         if(in1.getData() == null){
            //distribute (-) to terms in the 2nd list only
            term = new Term((in2.getData().coefficient)*-1, in2.getData().degree);
            ans.data.addLast(term);
            in2 = in2.getNext();
            continue;
         }
         if(in2.getData() == null){
            term = new Term(in1.getData().coefficient, in1.getData().degree);
            ans.data.addLast(term);
            in1 = in1.getNext();
            continue;
         }
        
         if(in1.getData().degree == in2.getData().degree){
            term = new Term((in1.getData().coefficient) - (in2.getData().coefficient), in1.getData().degree);
            if(term.coefficient != 0.0){
               ans.data.addLast(term);
            }
            in1 = in1.getNext();
            in2 = in2.getNext();
         }
         else if(in1.getData().degree > in2.getData().degree){
            term = new Term(in1.getData().coefficient, in1.getData().degree);
            ans.data.addLast(term);
            in1 = in1.getNext();
         }
         else if(in1.getData().degree < in2.getData().degree){
            term = new Term(-1*in2.getData().coefficient, in2.getData().degree);
            ans.data.addLast(term);
            in2 = in2.getNext();
         }
         
      }
      return ans;
   }
   
   public Polynomial multiply(Polynomial p) {
      Polynomial ans = new polyProject();
      Polynomial intermediate = new polyProject();
      Term term;
      DNode<Term> in1 = null,  in2 = null, reset = null;
      try { //reset: resets to the beginning of list so you can do next polynomial multiplication
         in1 = this.data.getFirst(); in2 = p.data.getFirst();
         reset = p.data.getFirst();
      } catch (Exception e) {
         e.printStackTrace();
      }
      while(true){
         //if the second list isn't empty, multiply term in 1 with term in 2, then move to next term in 2nd list
         if(in2.getData() != null){
            term = new Term(in1.getData().coefficient * in2.getData().coefficient,  in1.getData().degree +in2.getData().degree);
            intermediate.data.addLast(term);
            in2 = in2.getNext();
         }
         else{
            //if the ans is empty make intermediate the ans. Once you do this ans will never be empty again
            if(ans.data.isEmpty()){
               ans = intermediate;
               intermediate = new polyProject();
            }   
            else //add the current polynomial in ans with the newest polynomial obtained by distribution steps
               ans = ans.add(intermediate);
            in1 = in1.getNext();
            intermediate = new polyProject();
            //if we have reached the end of list 1 then done, otherwise reset 2nd list to continue
            if(in1.getData() == null)
               break;
            in2 = reset;
         }       
      }
      return ans;
   }
   
   public Polynomial divide(Polynomial p) throws Exception {
      Polynomial ans = new polyProject();
      return ans;
   }
   
   public Polynomial remainder(Polynomial p) throws Exception {
      Polynomial ans = new polyProject();
      return ans;
   }
}