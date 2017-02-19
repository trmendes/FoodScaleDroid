package tmendes.com.analyticalbalancedroid;

import android.content.Context;

@SuppressWarnings("ALL")
public class Product {
    private String name = "";
    private float weight;
    private float kg_price;
    private float ind_price;
    private final Context ctx;

    public Product(Context ctx, String name, float weight, float kg_price) {
        this.name = name;
        this.weight = weight;
        this.kg_price = kg_price;
        this.ind_price = this.kg_price * this.weight;
        this.ctx = ctx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getInd_price() {
        return ind_price;
    }

    public void setInd_price(float ind_price) {
        this.ind_price = ind_price;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getKg_price() {
        return kg_price;
    }

    public void setKg_price(float kg_price) {
        this.kg_price = kg_price;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(ctx.getResources().getString(R.string.prod_name_list));
        sb.append(" ");
        sb.append(this.getName());

        sb.append(" - ");

        sb.append(ctx.getResources().getString(R.string.prod_price_kg_list));
        sb.append(" ");
        sb.append(this.getKg_price());

        sb.append(" - ");

        sb.append(ctx.getResources().getString(R.string.prod_weight_list));
        sb.append(" ");
        sb.append(this.getWeight());

        sb.append(" - ");

        sb.append(ctx.getResources().getString(R.string.prod_to_pay_list));
        sb.append(" ");
        sb.append(String.format("%.02f", this.getInd_price()));

        return sb.toString();
    }
}
