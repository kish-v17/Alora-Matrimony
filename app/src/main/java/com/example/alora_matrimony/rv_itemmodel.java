package com.example.alora_matrimony;

public class rv_itemmodel extends UserDetails {
    int pfp;
    int bigpfp;
    int like;
    int more;

    public rv_itemmodel(int pfp, int bigpfp, int like, int more) {
        this.pfp = pfp;
        this.bigpfp = bigpfp;
        this.like = like;
        this.more = more;
    }

    public int getPfp() {
        return pfp;
    }

    public void setPfp(int pfp) {
        this.pfp = pfp;
    }

    public int getBigpfp() {
        return bigpfp;
    }

    public void setBigpfp(int bigpfp) {
        this.bigpfp = bigpfp;
    }

    public int getLike() {
            return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getMore() {
        return more;
    }

    public void setMore(int more) {
        this.more = more;
    }
}
