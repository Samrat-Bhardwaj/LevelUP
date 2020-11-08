public class Dstack extends MyStack{
    Dstack(){
        super(10);
    }

    Dstack(int n){
        super(n);
    }

    Dstack(int[] arr){
        super(2*arr.length);
        for(int ele:arr){
            super.push(ele);
        }
    }

    @Override
    public void push(int ele) {
        if(super.size()==super.capacity()){
            int[] temp=new int[super.size()];

            int i=temp.length-1;
            while(super.size()>0) temp[i]=super.pop();

            super.assignSize(2*temp.length);

            for(int e:temp){
                super.push_(e);
            }
            super.push(ele);
        }
    }
}