/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;



import java.util.*;


/**
 *
 * @author User
 */
public class operations extends GUI{
    
    String awnser = "0";
    public ArrayList<Object> calculatorList = new ArrayList<Object>();
    public ArrayList<Object> tempCalculatorList = new ArrayList<Object>();
    public float num1;
    public float num2;
    public int i;
    public boolean brackets = false;
    public boolean error = false;
    public int bracketReplaceLocation = 0;
    
    
    public String operations(ArrayList outputList){
        numberGrouper(outputList);
        while (true){

           //Brackets
            if (calculatorList.contains("(")&&calculatorList.contains(")")){
                brackets();
            }

            //Indecies
            else if (calculatorList.contains("²")||calculatorList.contains("√")){
                if (calculatorList.contains("²")){
                    locator("²");
                    square();
                }
                else{
                    locator("√");
                    squareRoot();
                }
            }

            //Multplication
            else if (calculatorList.contains("×")){
                locator("×");
                multiply();
            }

            //Divition
            else if (calculatorList.contains("÷")||calculatorList.contains("/")){
                if (calculatorList.contains("÷")){
                    locator("÷");
                }
                else{
                    locator("/");
                }
                divide();
                
            }

            //Addition
            else if (calculatorList.contains("+")){
                locator("+");
                add();
            }

            //Subtraction
            else if (calculatorList.contains("－")){
                locator("－");
                subtract();
            } 
            else if (error){
                awnser= "Syntax ERROR";
                break;
            }
            else{
                try{
                    if (brackets){
                        brackets = false;
                        brackets();
                    }
                    
                    else{
                        awnser= ""+calculatorList.get(0);
                        break;
                    }
                    
                }
                catch(Exception e){
                } 
            }
        }  
        return awnser;
    }
    
    
    public void numberGrouper(ArrayList outputList){
        String groupedNumbers = "";
        Iterator<String> it = outputList.iterator(); 
        while(it.hasNext()) {
            String next = it.next();
            try{
                Float.parseFloat(next);
                groupedNumbers=groupedNumbers+next;
            }
            catch(Exception e){
                if (next.equals(".")){
                   groupedNumbers=groupedNumbers+"."; 
                }
                else if (next.equals("-")){
                   groupedNumbers=groupedNumbers+"-"; 
                }
                else{
                    try{
                        calculatorList.add(Float.parseFloat(groupedNumbers));
                        calculatorList.add(next);
                    }
                    catch(Exception ex){
                        calculatorList.add(next);
                    }
                    groupedNumbers = ""; 
                } 
            }
        }
        try{
            calculatorList.add(Float.parseFloat(groupedNumbers));
            }
        catch(Exception e){
            if (!groupedNumbers.equals("")) {
                calculatorList.add(groupedNumbers);
            }
        }
    }
    
    public void locator(String symbol){

        //Locates symbol
        for (i = 0; i < (calculatorList.size()); i++){
            if (calculatorList.get(i).equals(symbol)){
                break;
            }
        } 
        
        
        try{
            //Gets adjoining numbers
            num1 = (float) ((calculatorList.get(i-1)));
            }
        catch(Exception e){
            if (symbol.equals("√")){
                num1=1;
            }
            else{
                error=true;
            }
        }
        
        try{
            //Gets adjoining numbers
            num2 = (float) ((calculatorList.get(i+1)));
            }
        catch(Exception e){
            if (symbol.equals("²")){
                num2=2;
            }
            else{
                error=true;
            }
        }
        
        
        for (int it=i+1;it > i-2;it--){
            try{
                //Removes used numbers
                calculatorList.remove(it); 
            }
            catch(Exception e){
            } 
        }  
    }
    
    //Brackets
    public void brackets(){ 
        ArrayList<String> bracketState = new ArrayList<String>();
        ArrayList<Integer> bracketLocation = new ArrayList<Integer>();

        //Checks if the list has anything if it does it puts the answer from the calculation back where it came
        if (!tempCalculatorList.isEmpty()){
            float tempAnswer = (float) calculatorList.get(0);
            calculatorList = tempCalculatorList;
            calculatorList.add(bracketReplaceLocation,tempAnswer);
        }
        
        //Finds all brackets and their location
        for (i = 0; i < (calculatorList.size()); i++){
            //Open bracket writer
            if (calculatorList.get(i).equals("(")){
                bracketState.add("(");
                bracketLocation.add(i);
            }
            //Closed bracket writer
            if (calculatorList.get(i).equals(")")){
                bracketState.add(")");
                bracketLocation.add(i);
            }
        }
        
        //Finds 2 brackets next to each other
        for (i = 0; i < (bracketState.size()); i++){
            if (bracketState.get(i).equals("(")){
                try{
                    if (bracketState.get(i+1).equals(")")){
                        brackets=true;
                        break;
                    }
                }
                catch(Exception e){
                    error=true;
                }    
            }
        }
        
        
        if (brackets){
            
            //Sets variables
            bracketReplaceLocation = bracketLocation.get(i);
            int firstBracket = bracketLocation.get(i);
            int secondBracket = bracketLocation.get(i+1);
            
            //Swaps tempCalculatorList and calculatorList
            tempCalculatorList = calculatorList;
            calculatorList = new ArrayList<Object>();

            //Gets the eqation inside the brackets
            for (i = 0; i < (tempCalculatorList.size()); i++){
                if (i>firstBracket&&i<secondBracket){
                    calculatorList.add(tempCalculatorList.get(i));
                }
            }
            
            //Removes the bracket and contents 
            int size=tempCalculatorList.size();
            for (i = secondBracket; i >= firstBracket; i--){
                try{
                    tempCalculatorList.remove(i);
                }
                catch(Exception e){
                }  
            } 
            //tempCalculatorList.remove(firstBracket); 
        } 
    }
    
    
    //Indices 
    //Square
    public void square(){  
        try{
            //Calculates
            calculatorList.add(i-1,Math.pow(num1,num2));
        }
        catch(Exception e){
            error=true;
        }
    }
    //SquareRoot
    public void squareRoot(){  
        try{
            
            //Calculates
            calculatorList.add(i-1,(num1*(Math.sqrt(num2))));
        }
        catch(Exception e){
            error=true;
        }
    }
    
    
    //Multiply
    public void multiply(){        
        try{
           //Calculates
           calculatorList.add(i-1,(num1*num2));   
        }
        catch(Exception e){
            error=true;
        }
    }
    
    
    //Divide
    public void divide(){
        try{
           //Calculates
           calculatorList.add(i-1,(num1/num2));   
        }
        catch(Exception e){
            error=true;
        }
    }
    
    
    //Add
    public void add(){
        try{
           //Calculates
           calculatorList.add(i-1,(num1+num2));   
        }
        catch(Exception e){
            error=true;
        }
    }
    
    
    //Subtract
    public void subtract(){
        try{
           //Calculates
           calculatorList.add(i-1,(num1-num2));   
        }
        catch(Exception e){
            error=true;
        }
    }


}
