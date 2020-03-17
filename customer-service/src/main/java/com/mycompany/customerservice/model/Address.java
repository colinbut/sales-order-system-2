package com.mycompany.customerservice.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "house_flat_no")
    private int houseFlatNo;

    @NotNull(message = "Address Line 1 cannot be null")
    @NotEmpty(message = "Address Line 1 cannot be empty")
    @Column(name = "address_line_1", length = 30, nullable = false)
    private String addressLine1;

    @NotNull(message = "Address Line 2 cannot be null")
    @NotEmpty(message = "Address Line 2 cannot be empty")
    @Column(name = "address_line_2", length = 30, nullable = false)
    private String addressLine2;

    @NotNull(message = "Postcode cannot be null")
    @NotEmpty(message = "Postcode cannot be empty")
    @Column(name = "postcode", length = 8, nullable = false)
    private String postCode;

    @NotNull(message = "City cannot be null")
    @NotEmpty(message = "City cannot be empty")
    @Column(name = "city", length = 15, nullable = false)
    private String city;

    @Column(name = "county", length = 15)
    private String county;

    @OneToOne(mappedBy = "address")
    private Customer customer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHouseFlatNo() {
        return houseFlatNo;
    }

    public void setHouseFlatNo(int houseFlatNo) {
        this.houseFlatNo = houseFlatNo;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return id == address.id &&
                houseFlatNo == address.houseFlatNo &&
                addressLine1.equals(address.addressLine1) &&
                addressLine2.equals(address.addressLine2) &&
                postCode.equals(address.postCode) &&
                city.equals(address.city) &&
                county.equals(address.county);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, houseFlatNo, addressLine1, addressLine2, postCode, city, county);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Address{");
        sb.append("id=").append(id);
        sb.append(", houseFlatNo=").append(houseFlatNo);
        sb.append(", addressLine1='").append(addressLine1).append('\'');
        sb.append(", addressLine2='").append(addressLine2).append('\'');
        sb.append(", postCode='").append(postCode).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", county='").append(county).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
