package com.mydemo.elektra.models;

public class SubItem {

    private String mainitem_id, name, description, image, price;

    public SubItem() {
    }

    public SubItem(String mainitem_id, String name, String description, String image, String price) {
        this.mainitem_id = mainitem_id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
    }

    public String getMainitem_id() {
        return mainitem_id;
    }

    public void setMainitem_id(String mainitem_id) {
        this.mainitem_id = mainitem_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }



    @Override
    public boolean equals (Object object) {
        boolean result = false;
        if (object == null || object.getClass() != getClass()) {
            result = false;
        } else {
            SubItem subitem = (SubItem) object;
            if (this.name.equals(subitem.getName()) && this.price.equals(subitem.getPrice())) {
                result = true;
            }
        }
        return result;
    }
}
