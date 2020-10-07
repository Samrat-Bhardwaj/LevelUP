#include<iostream>
#include<vector>
using namespace std;
void displayBoard(vector<vector<int>>& chess){
        for(int i = 0; i < chess.size(); i++){
            for(int j = 0; j < chess.size(); j++){
                cout<<chess[i][j] + " ";
            }
            cout<<"\n";
        \
        cout<<"\n";
}
void print(vector<vector<int>>& chess ,int row,int col,int move)
{
    if(row<0 || col<0 || row>=chess.size() || col>=chess.size() || chess[row][col]!=0)
    {
        return;
    }
    //cout<<"ok ";
    if(move==chess.size()*chess.size())
    {  
        chess[row][col]=move;
        displayBoard(chess);
        chess[row][col]=0;
        return;
    }
    chess[row][col]=move;
    print(chess,row-2,col+1,move+1);
    print(chess,row-1,col+2,move+1);
    print(chess,row+1,col+2,move+1);
    print(chess,row+2,col+1,move+1);
    print(chess,row+2,col-1,move+1);
    print(chess,row+1,col-2,move+1);
    print(chess,row-1,col-2,move+1);
    print(chess,row-2,col-1,move+1);

    chess[row][col]=0;
    
}

int main()
{
    int n=0,r=0,c=0;
    cin>>n>>r>>c;
    vector<vector<int>> chess(n,vector<int>(n,0));
    print(chess,r,c,1);
}