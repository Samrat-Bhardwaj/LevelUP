public class MyStack {
  private int[] st;
  private int tos=-1;
  private int size=0;
  
  public MyStack(){
      assignSize(10);
  }

  public MyStack(int n){
      assignSize(n);
  }

  public void assignSize(int n){
      this.st=new int[n];
  }

  @Override
  public String toString(){
      StringBuilder sb=new StringBuilder();
      sb.append("[");
      for(int i=0; i<this.size; i++){
          sb.append(this.st[i]);
          if(i!=this.size-1){
              sb.append(",");
          }
      }
      sb.append("]");
      return sb.toString();
  }

  public int size(){
      return this.size();
  }

  public boolean isEmpty(){
      return this.size==0 ? true : false;
  }

  public int capacity(){
      return this.st.length;
  } 

  public void push (int ele)throws Exception{
      if(this.size==this.st.length){
          throw new Exception("StackIsFull");
      }
      push_(ele);
  }

  public void push_(int ele){
      this.tos++;
      this.st[tos]=ele;
      this.size++;
  }

  public int top() throws Exception{
      if(this.size==0){
          return new Exception("Null Pointer Exception"+ -1);
      }
      return this.st[this.tos];
  }

  public int pop() throws Exception{
    if(this.size==0){
        return new Exception("Null Pointer Exception"+ -1);
    }
    int r=this.st[this.tos];
    this.size--;
    this.tos--;
    return r;
  }

}