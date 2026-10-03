public class Blackjack {
    public static String[] createDeck(){
        String [] number={"2","3","4","5","6","7","8","9","10","Jack","Queen","King","Ace"};
        String [] suits={"Clubs","Diamonds","Hearts","Spades"};
        int numberLenght=number.length;
        int suitsLength=suits.length;
        int N=suitsLength*numberLenght;
        String []  deck= new String[N];
        for(int i=0;i<suitsLength;i++){
            for(int j=0;j<numberLenght;j++)
                deck[i*numberLenght+j]=number[j]+" of "+suits[i];
                
        }
        return deck;

    }
    public static void shuffleDeck(String[] deck,int N){
        String temp;
        for(int i=0;i<N;i++){
            int num= i+ ((int) (Math.random()*(N-i)));
            temp=deck[i];
            deck[i]=deck[num];
            deck[num]=temp;
        }
        
    }
        
        public static Integer value(String singleCard){
            int choice;
            char firstValue=singleCard.charAt(0),secondValue =singleCard.charAt(1);
            if(secondValue=='0' || secondValue=='a'|| secondValue=='u'|| secondValue=='i')
                choice = 10;
            else if(secondValue=='c') choice = 1;
                else choice=firstValue - '0';
            
            return choice;
            }

            
        public static Integer []playersCounter(int playersCount,String [] deck,int valueFirst,int valueSecond,int nextCard,int aceAsEleven){
            String wantMore=" ",more;
            Integer [] playerC=new Integer[3];
            int playerHands=2,ace;
            if(playersCount<21)
               { StdOut.println("Do you want more?");
                wantMore=StdIn.readString();}
            while (wantMore.equalsIgnoreCase("yes") && playersCount < 21) {
                
                playerHands++;   
                more=deck[nextCard++];
                
                                
                StdOut.println("Your card is :"+more);

                
                playersCount+=value(more);
                while (playersCount > 21 && aceAsEleven > 0) {
                    playersCount -= 10;
                    aceAsEleven--;}
                if(value(more)==1 && playersCount+10 <=21 ){
                StdOut.println("Choose: press 1 for  "+playersCount+" OR press 2 for "+(playersCount+10));
                ace = StdIn.readInt();
                if(ace==2){
                    aceAsEleven++;
                    playersCount+=10;}
                }
                else if(playersCount==11 && (valueFirst==1 || valueSecond==1)) playersCount=21;
                
            
                if(playersCount<21){
                    StdOut.println("Do you want more?(Current value: "+playersCount+" , Press Yes to get another card)");
                    wantMore=StdIn.readString();}
                    
                
                
            }
            if(playersCount==21 && playerHands==2) StdOut.println("BlackJack!!!!!");
                else StdOut.println("Your Count = "+playersCount);
           
            playerC[0]=playersCount;
            playerC[1]=playerHands;
            playerC[2]=nextCard;
            return playerC;
        }
        public static String determineWinner(int playersCount,int dealersCount,int playerHands,boolean split){
            String winner=" ";
            if (playersCount > 21) {
                winner = "dealer";
                } else if (playersCount > dealersCount || dealersCount > 21) {
                        if (playersCount == 21 && playerHands == 2 && split) {
                             winner = "player21";}
                            else {
                            winner = "player";
                                             }
                        } else if (playersCount == dealersCount) {
                            winner = "draw";
                     }   else {
                            winner = "dealer";}
                                                
             return winner;


        }
        public static int whoWon(String []deck,int N,int money,int bet){
            int playersCount=0,dealersCount=0,playerHands1=2,playerHands2=2,aceAsEleven=0,aceAsElevenDealer=0,playersCount1=0,playersCount2=0;
            String more,split=" ";
            String dealersFirst="No",dealersSecond="No",playersFirst="No",playersSecond="No";
            Integer[] pCounter1 = new  Integer [2];
            Integer[] pCounter2 = new  Integer [2];
           
            
            shuffleDeck(deck, N );
            int nextCard=0,ace=1;
            dealersFirst=deck[nextCard++];
            dealersSecond=deck[nextCard++];
            playersSecond=deck[nextCard++];
            playersFirst=deck[nextCard++];

            
            StdOut.println("Your cards are:"+ playersFirst+" and "+ playersSecond);
            StdOut.println("Dealer has: "+dealersFirst+" ? ");
            int valueFist=value(playersFirst);
            int valueSecond=value(playersSecond);
            playersCount=valueFist+valueSecond;
            if(valueFist==valueSecond && 2*bet<=money) {
                StdOut.println("Do you want to Split?(Type Yes if you want to !");
                split=StdIn.readString();}
            
                
            if(playersCount==11 && (valueSecond==1 || valueFist==1))playersCount=21;
            else if((valueFist==1 || valueSecond==1 )&& playersCount!=11){
                StdOut.println("Choose: press 1 for  "+playersCount+" OR press 2 for "+(playersCount+10));
                ace = StdIn.readInt();}
                if(ace==2) {
                    aceAsEleven ++;
                    playersCount+=10;
            } 
            
            dealersCount=value(dealersFirst)+value(dealersSecond);
            if(value(dealersFirst)==1 || value(dealersSecond)==1){
                dealersCount+=10;
                aceAsElevenDealer++;}
            StdOut.println("Dealers Count : "+(value(dealersFirst)));
            StdOut.println("Your Count : "+playersCount);
            
            
            
            
            if(split.equals("Yes") || split.equals("yes")){
                StdOut.println("Split 1:");
                pCounter1 = playersCounter(playersCount-valueFist, deck, valueFist, valueSecond, nextCard,aceAsEleven);
                playerHands1=pCounter1[1]-1;
                playersCount1=pCounter1[0];
                nextCard=pCounter1[2];
                StdOut.println("Split 2: ");
                pCounter2 = playersCounter(playersCount-valueFist, deck, valueFist, valueSecond, nextCard,aceAsEleven);
                playerHands2=pCounter2[1]-1;;
                playersCount2=pCounter2[0];
                nextCard=pCounter2[2];
                                    }
            else {pCounter1 = playersCounter(playersCount, deck, valueFist, valueSecond, nextCard,aceAsEleven);
            playerHands1=pCounter1[1];
            playersCount1=pCounter1[0];
            nextCard=pCounter1[2];}
            StdOut.println("Dealer's second card is: "+dealersSecond+" dealers count is: "+dealersCount);
            if(playersCount1<22 || (split.equalsIgnoreCase("Yes") && playersCount2<22)){
                while(dealersCount<17  ){
                
                more=deck[nextCard++];
                

                dealersCount+=value(more);
                
                if(value(more)==1 && dealersCount+10<22) {
                    aceAsElevenDealer++;
                    dealersCount+=10;}
                while (dealersCount > 21 && aceAsElevenDealer > 0) {
                    dealersCount -= 10;
                    aceAsElevenDealer--;}

                StdOut.print("Dealers next card: "+more);
                StdOut.println("Dealers Count: "+dealersCount);
                

            }}
            if(split.equals("Yes") || split.equals("yes")) {
                if(determineWinner(playersCount2, dealersCount, playerHands2,false).equals("player")){
                    StdOut.println("Split 2 wins");
                    money+=bet;

                }else if (determineWinner(playersCount2, dealersCount, playerHands2,false).equals("draw")) {
                    StdOut.println("Draw");
                    
                }else{ StdOut.println("Split 2 loses");
                       money-=bet;}
                if(determineWinner(playersCount1, dealersCount, playerHands1,false).equals("player")){
                    StdOut.println("Split 1 wins");
                    money+=bet;

                }else if (determineWinner(playersCount1, dealersCount, playerHands1,false).equals("draw")) {
                    StdOut.println("Draw");
                    
                }else{ StdOut.println("Split 1 loses");
                       money-=bet;}}
            else{ if(determineWinner(playersCount1, dealersCount, playerHands1,true).equals("player")){
                    StdOut.println("You win");
                    money+=bet;

                }else if(determineWinner(playersCount1, dealersCount, playerHands1,true).equals("player21")){
                    StdOut.println("You win with Blackjack!!");
                    money = money + bet*3;
                }
                else if (determineWinner(playersCount1, dealersCount, playerHands1,true).equals("draw")) {
                    StdOut.println("Draw");
                    
                }else{ StdOut.println("You lose");
                       money-=bet;}

                }
                
                      return money; 


            
            
        }
    
    


            
            




        

    
    public static void main(String[] args){
        StdOut.println("Enter the amount of money you want to start with:");
        int bet,money = StdIn.readInt(),moneyFirst;
        while(money<1){
            StdOut.println("Money needs to be more than 0 in order to play. Please give again!!!");
            money=StdIn.readInt();
        }
        int round=1;
        String [] deck = createDeck();
        String nextRound;
        StdOut.println("Lets start round 1:");
        nextRound="yes";
        int N=deck.length;
        StdOut.println("Enter the bet for the first round: ");
        moneyFirst=money;
        
        while ((nextRound.equalsIgnoreCase("yes") || nextRound.equalsIgnoreCase("Yes"))&& money > 0){
            bet=StdIn.readInt();
            while(bet>money || bet<1){
                StdOut.println("The bet needs to be less than the money and more than 0. Give again: ");
                bet=StdIn.readInt();}
            money= whoWon(deck, N, money, bet);
            
            StdOut.println("New amount is: "+money);
            if(money>0){
                StdOut.println("Play next round?(Press Yes , in order  to play");
                nextRound = StdIn.readString(); }
            round++;
            if((nextRound.equals("Yes") ||nextRound.equals("yes") )&& money>0)
                StdOut.println("Enter the bet for round "+ round );
                

        }
        StdOut.println("The money you started were: "+moneyFirst+"$. The money you are leaving blackjack are: "+money+ "$");
        StdOut.println("See you next time!!!Bye!!!");


            
        

        
        

        
    }
    
}
