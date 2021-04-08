package com.food.localresto.util;

import android.content.Context;

import com.food.localresto.R;
import com.food.localresto.SettingActivity;
import com.food.localresto.entities.CartObject;
import com.food.localresto.entities.SalesModel;

public class PrintReceipt {
    public static boolean  printBillFromOrder(Context context, String nama, String Alamat, String Kota, String Id, String Tgl, String Jam, Double Bayar, Double Pajak, Double Disc , String footer_struk, CartObject[] finalListToPrint){
        if(SettingActivity.BLUETOOTH_PRINTER.IsNoConnection()){
            return false;
        }

        byte[] cc = new byte[]{0x1B,0x21,0x00};  // 0- normal size text
        byte[] bb = new byte[]{0x1B,0x21,0x08};  // 1- only bold text
        byte[] bb2 = new byte[]{0x1B,0x21,0x20}; // 2- bold with medium text
        byte[] bb3 = new byte[]{0x1B,0x21,0x10}; // 3- bold with large text

        double totalItem=0.00, totalBill=0.00, netBill=0.00, totalVat=0.00, totalDis=0.00, totalBayar=0.00, totalKembali=0.00;

        //LF = Line feed
        SettingActivity.BLUETOOTH_PRINTER.Begin();
        SettingActivity.BLUETOOTH_PRINTER.LF();
        SettingActivity.BLUETOOTH_PRINTER.LF();
        SettingActivity.BLUETOOTH_PRINTER.SetAlignMode((byte) 1);//CENTER
        SettingActivity.BLUETOOTH_PRINTER.SetLineSpacing((byte) 30);	//30 * 0.125mm
        SettingActivity.BLUETOOTH_PRINTER.SetFontEnlarge((byte) 0x1B);//normal
        SettingActivity.BLUETOOTH_PRINTER.BT_Write(bb2);
        SettingActivity.BLUETOOTH_PRINTER.BT_Write("\n"+nama);

        SettingActivity.BLUETOOTH_PRINTER.SetAlignMode((byte) 1);//CENTER
        SettingActivity.BLUETOOTH_PRINTER.SetLineSpacing((byte) 30);	//30 * 0.125mm
        SettingActivity.BLUETOOTH_PRINTER.SetFontEnlarge((byte) 0x00);//normal
        SettingActivity.BLUETOOTH_PRINTER.BT_Write("\n"+Alamat);
        SettingActivity.BLUETOOTH_PRINTER.BT_Write("\n"+Kota);

        SettingActivity.BLUETOOTH_PRINTER.LF();
        SettingActivity.BLUETOOTH_PRINTER.SetAlignMode((byte) 0);//LEFT
        SettingActivity.BLUETOOTH_PRINTER.SetLineSpacing((byte) 30);	//50 * 0.125mm
        SettingActivity.BLUETOOTH_PRINTER.SetFontEnlarge((byte) 0x00);//normal font

        //BT_Write() method will initiate the printer to start printing.
        String id = "#"+Id.substring(10);
        String datena = Tgl;
        String jamna = Jam;

        String t1 = String.format("%10s",datena);
        String t2 = String.format("%10s"," ");
        String t3 = String.format("%8s",jamna);
        String t4 = String.format("%12s",id);
        String t5 = String.format("%12s","Admin");


        //BT_Write() method will initiate the printer to start printing.
        //SettingActivity.BLUETOOTH_PRINTER.BT_Write("\n\nInvoice No: " + id +
        //        "\nDate: " + datena +
        //        "\nTime: " + jamna );

        SettingActivity.BLUETOOTH_PRINTER.BT_Write( t1 + t2 + t4 );
        SettingActivity.BLUETOOTH_PRINTER.BT_Write("\n"+ t3 + t2 + t5);

        SettingActivity.BLUETOOTH_PRINTER.LF();
        SettingActivity.BLUETOOTH_PRINTER.BT_Write("\n"+context.getResources().getString(R.string.print_line)+"\n");
        SettingActivity.BLUETOOTH_PRINTER.LF();

        SettingActivity.BLUETOOTH_PRINTER.SetAlignMode((byte) 0);//LEFT
        SettingActivity.BLUETOOTH_PRINTER.SetLineSpacing((byte) 30);	//50 * 0.125mm
        SettingActivity.BLUETOOTH_PRINTER.SetFontEnlarge((byte) 0x00);//normal font

        //static sales record are generated
        //SalesModel.generatedMoneyReceipt();


        if(finalListToPrint == null){
            return false;
        }

        StaticValue.arrayListSalesModel.clear();
        for (int i = 0; i < finalListToPrint.length; i++){
            float total = (float) 0;
            int quantity = finalListToPrint[i].getQuantity();
            if(quantity == 0){
                quantity = 1;
            }
            float itemSubtotal = quantity * finalListToPrint[i].getPrice();
            total += itemSubtotal;

            SalesModel salesModel = new SalesModel(finalListToPrint[i].getOrderName(), finalListToPrint[i].getQuantity(), finalListToPrint[i].getPrice());
            StaticValue.arrayListSalesModel.add(salesModel);
        }

        //static sales record are generated
        //SalesModel.generatedMoneyReceipt();

        for(int i=0;i<StaticValue.arrayListSalesModel.size();i++){
            SalesModel salesModel = StaticValue.arrayListSalesModel.get(i);


            /*SettingActivity.BLUETOOTH_PRINTER.LF();
            SettingActivity.BLUETOOTH_PRINTER.BT_Write("\n"+salesModel.getProductShortName()+"    "
                    + salesModel.getSalesAmount() + "    "
                    + salesModel.getUnitSalesCost() +"   "
                    + StaticValue.CURRENCY);
            SettingActivity.BLUETOOTH_PRINTER.LF();*/

            String test1 = String.format("%-14s",salesModel.getProductShortName());
            String test2 = String.format("%10s"," ");
            String test3 = String.format("%-3d",salesModel.getSalesAmount());
            String test4 = String.format("%7s",StaticValue.CURRENCY);
            //String test5 = String.format("%8d",(int)salesModel.getUnitSalesCost());
            String tt5 = String.format("%1$,.0f",salesModel.getUnitSalesCost());
            String test5 = String.format("%8s",tt5);

            totalItem=salesModel.getUnitSalesCost() * salesModel.getSalesAmount();
            //String test6 = String.format("%11d",(int)totalItem);
            String tt6 = String.format("%1$,.0f",totalItem);
            String test6 = String.format("%11s",tt6);

            SettingActivity.BLUETOOTH_PRINTER.SetAlignMode((byte) 0);//LEFT
            SettingActivity.BLUETOOTH_PRINTER.SetLineSpacing((byte) 30);	//50 * 0.125mm
            SettingActivity.BLUETOOTH_PRINTER.SetFontEnlarge((byte) 0x00);//normal font
            SettingActivity.BLUETOOTH_PRINTER.BT_Write(test1);
            SettingActivity.BLUETOOTH_PRINTER.LF();

            //SettingActivity.BLUETOOTH_PRINTER.SetAlignMode((byte) 1);//RIGHT
            //SettingActivity.BLUETOOTH_PRINTER.SetLineSpacing((byte) 30);	//50 * 0.125mm
            //SettingActivity.BLUETOOTH_PRINTER.SetFontEnlarge((byte) 0x00);//normal font
            SettingActivity.BLUETOOTH_PRINTER.BT_Write("\n"+test2
                    + test3
                    + test5
                    + test6);

            //SettingActivity.BLUETOOTH_PRINTER.SetAlignMode((byte) 0);//LEFT
            //SettingActivity.BLUETOOTH_PRINTER.SetLineSpacing((byte) 30);	//50 * 0.125mm
            //SettingActivity.BLUETOOTH_PRINTER.SetFontEnlarge((byte) 0x00);//normal font
            //SettingActivity.BLUETOOTH_PRINTER.BT_Write("\n"+test1
            // + salesModel.getSalesAmount() + "    "
            // + StaticValue.CURRENCY +"   "
            // + salesModel.getProductShortName()
            //);
            //SettingActivity.BLUETOOTH_PRINTER.printString(test1);

            //SettingActivity.BLUETOOTH_PRINTER.SetAlignMode((byte) 1);//CENTER
            //SettingActivity.BLUETOOTH_PRINTER.SetLineSpacing((byte) 30);	//50 * 0.125mm
            //SettingActivity.BLUETOOTH_PRINTER.SetFontEnlarge((byte) 0x00);//normal font
            //SettingActivity.BLUETOOTH_PRINTER.BT_Write("\n"+test2
            // + salesModel.getSalesAmount() + "    "
            // + StaticValue.CURRENCY +"   "
            // + salesModel.getUnitSalesCost()
            //        );
            //SettingActivity.BLUETOOTH_PRINTER.printString(test2);

            /*SettingActivity.BLUETOOTH_PRINTER.SetAlignMode((byte) 2);//RIGHT
            SettingActivity.BLUETOOTH_PRINTER.SetLineSpacing((byte) 30);	//50 * 0.125mm
            SettingActivity.BLUETOOTH_PRINTER.SetFontEnlarge((byte) 0x00);//normal font
            SettingActivity.BLUETOOTH_PRINTER.BT_Write("\n"+StaticValue.CURRENCY +" "
                    + salesModel.getUnitSalesCost());*/

            //SettingActivity.BLUETOOTH_PRINTER.printString(test3);

            //SettingActivity.BLUETOOTH_PRINTER.LF();

            totalBill=totalBill + (salesModel.getUnitSalesCost() * salesModel.getSalesAmount());
        }
        SettingActivity.BLUETOOTH_PRINTER.SetAlignMode((byte) 1);
        SettingActivity.BLUETOOTH_PRINTER.SetLineSpacing((byte) 30);	//50 * 0.125mm
        SettingActivity.BLUETOOTH_PRINTER.SetFontEnlarge((byte)0x00);//normal font
        SettingActivity.BLUETOOTH_PRINTER.BT_Write("\n"+context.getResources().getString(R.string.print_line)+"\n");


        SettingActivity.BLUETOOTH_PRINTER.SetAlignMode((byte) 2);//RIGHT
        SettingActivity.BLUETOOTH_PRINTER.SetLineSpacing((byte) 30);	//50 * 0.125mm
        SettingActivity.BLUETOOTH_PRINTER.SetFontEnlarge((byte)0x00);//normal font

        //pajak VAT
        //if(StaticValue.VAT > 0){
        if(Pajak > 0){
            totalVat=Double.parseDouble(Utility.doubleFormatter(totalBill*(Pajak/100)));
        }
        if(Disc > 0){
            totalDis=-1*(Double.parseDouble(Utility.doubleFormatter(totalBill*(Disc/100))));
        }

        netBill=totalBill+totalVat+totalDis;
        String strTtkDua = String.format("%2s",":");

        if(Pajak > 0 || Disc > 0){
            //SettingActivity.BLUETOOTH_PRINTER.LF();
            //SettingActivity.BLUETOOTH_PRINTER.BT_Write("Total Bill:" + StaticValue.CURRENCY  + "" + Utility.doubleFormatter(totalBill)+"\n");
            String strTot = String.format("%-8s","Total Bill");
            //String totBil = String.format("%12d",(int)totalBill);
            String totB = String.format("%1$,.0f",totalBill);
            String totBil = String.format("%12s",totB);

            SettingActivity.BLUETOOTH_PRINTER.BT_Write(strTot + strTtkDua   + totBil+"\n");


            SettingActivity.BLUETOOTH_PRINTER.LF();
            //SettingActivity.BLUETOOTH_PRINTER.BT_Write(Double.toString(StaticValue.VAT) + "% VAT:" + StaticValue.CURRENCY + "" +
            //        Utility.doubleFormatter(totalVat)+"\n");
            if(Pajak > 0){
                int p = Pajak.intValue();

                String strVat = String.format("%-10s","Pajak "+p+"%");
                //String totVat = String.format("%12d",(int)totalVat);
                String totV = String.format("%1$,.0f",totalVat);
                String totVat = String.format("%12s",totV);
                SettingActivity.BLUETOOTH_PRINTER.BT_Write(strVat + strTtkDua   + totVat+"\n");
            }

            if(Disc > 0) {
                int d = Disc.intValue();

                String strDis = String.format("%-10s", "Disc "+d+"%");
                //String totDis = String.format("%12d", (int) totalDis);
                String totD = String.format("%1$,.0f",totalDis);
                String totDis = String.format("%12s",totD);
                SettingActivity.BLUETOOTH_PRINTER.BT_Write(strDis + strTtkDua + totDis + "\n");
            }

            SettingActivity.BLUETOOTH_PRINTER.LF();
            SettingActivity.BLUETOOTH_PRINTER.SetAlignMode((byte) 1);//center
            SettingActivity.BLUETOOTH_PRINTER.BT_Write(context.getResources().getString(R.string.print_line)+"\n");


            SettingActivity.BLUETOOTH_PRINTER.LF();
            SettingActivity.BLUETOOTH_PRINTER.SetLineSpacing((byte) 30);
            SettingActivity.BLUETOOTH_PRINTER.SetAlignMode((byte) 2);//Right
            SettingActivity.BLUETOOTH_PRINTER.SetFontEnlarge((byte) 0x9);
            //SettingActivity.BLUETOOTH_PRINTER.BT_Write("Net Bill:" + StaticValue.CURRENCY + "" +Utility.doubleFormatter(netBill) +"\n");

        }

        String strNet = String.format("%-10s","Total");
        //String totNet = String.format("%12d",(int)netBill);
        String totN = String.format("%1$,.0f",netBill);
        String totNet = String.format("%12s",totN);

        SettingActivity.BLUETOOTH_PRINTER.BT_Write(strNet + strTtkDua   + totNet+"\n");

        String strBayar = String.format("%-10s","Bayar");
        //String strTtkDua = String.format("%-2s",":");
        totalBayar = Bayar;
        //String totBayar = String.format("%12d",(int)totalBayar);
        String totB = String.format("%1$,.0f",totalBayar);
        String totBayar = String.format("%12s",totB);

        SettingActivity.BLUETOOTH_PRINTER.BT_Write(strBayar + strTtkDua   + totBayar+"\n");


        String strKembali = String.format("%-10s","Kembali");
        totalKembali = Bayar - netBill;
        //String jarak = String.format("%12d","");
        String totKembali1 =String.format("%1$,.0f", totalKembali);
        String totKembali2 = String.format("%12s",totKembali1);

        SettingActivity.BLUETOOTH_PRINTER.BT_Write(strKembali + strTtkDua   + totKembali2 +"\n");

        SettingActivity.BLUETOOTH_PRINTER.LF();
        SettingActivity.BLUETOOTH_PRINTER.SetAlignMode((byte) 1);//center
        SettingActivity.BLUETOOTH_PRINTER.SetFontEnlarge((byte) 0x00);//normal font
        SettingActivity.BLUETOOTH_PRINTER.BT_Write(context.getResources().getString(R.string.print_line));

        SettingActivity.BLUETOOTH_PRINTER.LF();
        SettingActivity.BLUETOOTH_PRINTER.SetAlignMode((byte) 1);//center
        SettingActivity.BLUETOOTH_PRINTER.SetFontEnlarge((byte) 0x00);//normal font
        SettingActivity.BLUETOOTH_PRINTER.BT_Write("");

        SettingActivity.BLUETOOTH_PRINTER.BT_Write("\n" + footer_struk +"\n");


        SettingActivity.BLUETOOTH_PRINTER.LF();
        SettingActivity.BLUETOOTH_PRINTER.LF();
        SettingActivity.BLUETOOTH_PRINTER.LF();
        SettingActivity.BLUETOOTH_PRINTER.LF();

        return true;
    }
}
