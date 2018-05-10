package com.anyun.cloud.model.entity;

import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.iciql.Iciql.EnumType;
import com.iciql.Iciql.IQColumn;
import com.iciql.Iciql.IQEnum;
import com.iciql.Iciql.IQTable;

@IQTable(name = "product_info") //定义表名称（如果表名称一直不需要帝定义,默认插入数据的时候如果不存在自动创建表）
public class ProductEntity extends AbstractEntity {
    @IQColumn(name = "id", primaryKey = true, autoIncrement = true)
    public Integer productId;

    @IQColumn(name = "product_name", length = 200, trim = true)
    public String productName;

    @IQColumn(name = "category", length = 50, trim = true)
    public String category;

    @IQColumn(name = "unit_price")
    public Double unitPrice;

    @IQColumn(name = "units_in_stock")
    public Integer unitsInStock;

    @IQColumn(name = "reorder_quantity")
    private Integer reorderQuantity;

    // 没有  @IQColumn  默认忽略此字段
    private Integer ignoredField;

    public ProductEntity() {
        // 默认的构造函数
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(Integer unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    public Integer getReorderQuantity() {
        return reorderQuantity;
    }

    public void setReorderQuantity(Integer reorderQuantity) {
        this.reorderQuantity = reorderQuantity;
    }

    public Integer getIgnoredField() {
        return ignoredField;
    }

    public void setIgnoredField(Integer ignoredField) {
        this.ignoredField = ignoredField;
    }

    //重写　toString()
    @Override
    public String toString() {
        return "ProductEntity{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", unitPrice=" + unitPrice +
                ", unitsInStock=" + unitsInStock +
                ", reorderQuantity=" + reorderQuantity +
                ", ignoredField=" + ignoredField +
                '}';
    }
}