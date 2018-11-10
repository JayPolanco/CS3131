class DList<T> {
   private DNode<T> header, trailer;
   private int size;

   public DList() {
      size = 0;
      header = new DNode<T>(null, null, null);
      trailer = new DNode<T>(null, header, null);
      header.setNext(trailer);
   }

   // utility methods

   public int size() {
      return size;
   }

   public boolean isEmpty() {
      return size == 0;
   }

   // give clients access to nodes, but not to the header or trailer

   public DNode<T> getFirst() throws Exception {
      if (isEmpty())
         throw new Exception("Empty");
      return header.getNext();
   }

   public DNode<T> getLast() throws Exception {
      if (isEmpty())
         throw new Exception("Empty");
      return trailer.getPrev();
   }

   public DNode<T> getNext(DNode<T> v) throws Exception {
      DNode<T> ans = v.getNext();
      if (ans == null || ans == trailer)
         throw new Exception("No such node");
      return ans;
   }

   public DNode<T> getPrev(DNode<T> v) throws Exception {
      DNode<T> ans = v.getPrev();
      if (ans == null || ans == header)
         throw new Exception("No such node");
      return ans;
   }

   // methods to change the list

   public void addBefore(T d, DNode<T> v) {
      DNode<T> u = v.getPrev();
      DNode<T> x = new DNode<T>(d, u, v);
      u.setNext(x);
      v.setPrev(x);
      size++;
   }

   public void addAfter(T d, DNode<T> v) {
      DNode<T> w = v.getNext();
      DNode<T> x = new DNode<T>(d, v, w);
      v.setNext(x);
      w.setPrev(x);
      size++;
   }

   public void addFirst(T d) {
      addAfter(d, header);
   }

   public void addLast(T d) {
      addBefore(d, trailer);
   }

   public T remove(DNode<T> v) throws Exception {
      if (v == header || v == trailer)
         throw new Exception("Sentinel");
      DNode<T> u = v.getPrev();
      DNode<T> w = v.getNext();
      w.setPrev(u);
      u.setNext(w);
      size--;
      return v.getData();
   }

   // LinkedList testing methods:

   public String toString() {
      String ans = "";
      DNode<T> n = header;
      ans += "(H)<-->";
      do {
         n = n.getNext();
         if (n == trailer)
            ans += "(T)";
         else
            ans += (n.getData() + "<-->");
      } while (n != trailer);
      return ans;
   }
}