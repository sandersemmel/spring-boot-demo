package com.example.demo.dto;

import com.example.demo.discountAgreement.entity.AgreementType;

public class DTO_DiscountAgreement {

    private Long productId;
    private AgreementType agreementType;
    private Integer percentageOff;
    private Integer mustBuyAmount;
    private Integer onlyPayForAmount;


    public DTO_DiscountAgreement(Long productId, AgreementType agreementType, Integer percentageOff,
            Integer mustBuyAmount, Integer onlyPayForAmount, Long customerID) {
        this.productId = productId;
        this.agreementType = agreementType;
        this.percentageOff = percentageOff;
        this.mustBuyAmount = mustBuyAmount;
        this.onlyPayForAmount = onlyPayForAmount;
    }


    public DTO_DiscountAgreement(){

    }


    public AgreementType getAgreementType() {
        return agreementType;
    }

    public void setAgreementType(AgreementType agreementType) {
        this.agreementType = agreementType;
    }

    public Long getProductId() {
        return productId;
    }


    public void setProductId(Long productId) {
        this.productId = productId;
    }


    public Integer getPercentageOff() {
        return percentageOff;
    }
    public void setPercentageOff(Integer percentageOff) {
        this.percentageOff = percentageOff;
    }
    public Integer getMustBuyAmount() {
        return mustBuyAmount;
    }
    public void setMustBuyAmount(Integer mustBuyAmount) {
        this.mustBuyAmount = mustBuyAmount;
    }
    public Integer getOnlyPayForAmount() {
        return onlyPayForAmount;
    }
    public void setOnlyPayForAmount(Integer onlyPayForAmount) {
        this.onlyPayForAmount = onlyPayForAmount;
    }

}
