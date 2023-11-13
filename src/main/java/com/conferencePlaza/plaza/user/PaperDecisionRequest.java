package com.conferencePlaza.plaza.user;

public class PaperDecisionRequest {

    public long paperId;
    public String decision;

    public PaperDecisionRequest() {
    }

    public PaperDecisionRequest(long paperId, String decision) {
        this.paperId = paperId;
        this.decision = decision;
    }

    public long getPaperId() {
        return paperId;
    }

    public void setPaperId(long paperId) {
        this.paperId = paperId;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }
}
