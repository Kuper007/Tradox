package com.nc.tradox.utilities;

public class MedicineTest {

    public static void main(String[] args) throws Exception{
        MedicineApi medicineApi = new MedicineApi();
        System.out.println(medicineApi.medicineList().toString());
    }

}
