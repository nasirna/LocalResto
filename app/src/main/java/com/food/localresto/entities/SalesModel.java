package com.food.localresto.entities;

import com.food.localresto.util.StaticValue;

public class SalesModel {
    String productShortName;
    int salesAmount;
    double unitSalesCost;

    public SalesModel(String productSName, int amount, double unitSCost){
        this.productShortName = productSName;
        this.salesAmount = amount;
        this.unitSalesCost = unitSCost;
    }

    public SalesModel(){

    }

    public static void generatedMoneyReceipt(){
        SalesModel salesModel = new SalesModel("Karpet Tebal", 1, 15000);
        StaticValue.arrayListSalesModel.add(salesModel);
        SalesModel salesModel1 = new SalesModel("Karpet", 20, 1000);
        StaticValue.arrayListSalesModel.add(salesModel1);
        //SalesModel salesModel2 = new SalesModel("Karpet", 1, 5000);
        //StaticValue.arrayListSalesModel.add(salesModel2);

    }

    public String getProductShortName() {
        return productShortName;
    }

    public int getSalesAmount() {
        return salesAmount;
    }

    public double getUnitSalesCost() {
        return unitSalesCost;
    }


}
