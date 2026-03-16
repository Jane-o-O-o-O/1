package com.ruoyi.project.employment.domain;

import com.ruoyi.framework.web.domain.BaseEntity;

public class EmploymentEnterpriseProfile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long enterpriseId;
    private Long userId;
    private String companyName;
    private String industryName;
    private String city;
    private String address;
    private String companySize;
    private String contactName;
    private String contactPhone;
    private String companyIntro;
    private String certificationStatus;
    private String licenseFileUrl;

    public Long getEnterpriseId() { return enterpriseId; }
    public void setEnterpriseId(Long enterpriseId) { this.enterpriseId = enterpriseId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getIndustryName() { return industryName; }
    public void setIndustryName(String industryName) { this.industryName = industryName; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getCompanySize() { return companySize; }
    public void setCompanySize(String companySize) { this.companySize = companySize; }
    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }
    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
    public String getCompanyIntro() { return companyIntro; }
    public void setCompanyIntro(String companyIntro) { this.companyIntro = companyIntro; }
    public String getCertificationStatus() { return certificationStatus; }
    public void setCertificationStatus(String certificationStatus) { this.certificationStatus = certificationStatus; }
    public String getLicenseFileUrl() { return licenseFileUrl; }
    public void setLicenseFileUrl(String licenseFileUrl) { this.licenseFileUrl = licenseFileUrl; }
}
