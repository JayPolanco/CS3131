class Term {
   
   Double coefficient;
   int degree;
   
   public Term() {
      this(null, 0);
   }
   public boolean isPositive() {
      return coefficient > 0;
   }
   public Term(Double coefficient, int degree) {
      this.coefficient = coefficient;
      this.degree = degree;
   }
   public Double getCoefficient() {
      return coefficient;
   }
   public void setCoefficient(Double coefficient) {
      this.coefficient = coefficient;
   }
   public int getDegree() {
      return degree;
   }
   public void setDegree(int degree) {
      this.degree = degree;
   }
   public String toString() {
      String ans = "";
      if (coefficient.doubleValue() == 0) return "";
      if (degree == 0) return coefficient.toString();
      if (coefficient != 1) {
         if (coefficient == -1) ans += "-";
         else ans += coefficient + " ";
      }
      ans = ans + "X";
      if (degree == 1) return ans;
      return ans + "^" + degree;
   }
}