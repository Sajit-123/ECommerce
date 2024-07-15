package com.eCommerece.major.global;

import java.util.ArrayList;
import java.util.List;

import com.eCommerece.major.model.Products;

public class GlobalData {
    public static List<Products> cart;
    static {
        cart = new ArrayList<Products>();
    }
}
