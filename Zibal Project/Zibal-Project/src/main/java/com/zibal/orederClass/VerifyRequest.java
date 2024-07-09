package com.zibal.orederClass;

public class VerifyRequest {
    private String merchant;
    private Long trackId;

    public VerifyRequest(Long trackId, String merchant) {
        this.trackId = trackId;
        this.merchant = merchant;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public Long getTrackId() {
        return trackId;
    }

    public void setTrackId(Long trackId) {
        this.trackId = trackId;
    }
}
