package com.example.taphoaapp.Basket;

public interface DataCommunication {

    public boolean getaddtoBasket();

    public void setaddtoBasket(boolean getaddtoBasket);

    public String getPassName();

    public void setPassName(String passName);
    public String getPassCategory();


    public void setPassCategory(String passCategory) ;

    public int getPassquantity();

    public void setPassquantity(int passquantity);

    public String getPasscolor() ;

    public void setPasscolor(String passcolor);

    public String getPasssize();

    public void setPasssize(String passsize);

    public int getPassPrice();

    public void setPassPrice(int passPrice);

    public int getPassSoluong();

    public void setPassSoluong(int passSoluong) ;

    public void setPrevActive(String PrevActive);
    public String getPrevActive();
}

