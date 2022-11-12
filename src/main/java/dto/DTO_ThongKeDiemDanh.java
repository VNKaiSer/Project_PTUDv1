package dto;

public class DTO_ThongKeDiemDanh {
    private Object caNhan;
    private int coMat;
    private int vang;
    private int coPhep;

    public DTO_ThongKeDiemDanh() {
    }

    public DTO_ThongKeDiemDanh(Object caNhan, int coMat, int vang, int coPhep) {
        this.caNhan = caNhan;
        this.coMat = coMat;
        this.vang = vang;
        this.coPhep = coPhep;
    }

    public Object getCaNhan() {
        return caNhan;
    }

    public int getCoMat() {
        return coMat;
    }

    public int getVang() {
        return vang;
    }

    public int getCoPhep() {
        return coPhep;
    }

    public void setCaNhan(Object caNhan) {
        this.caNhan = caNhan;
    }

    public void setCoMat(int coMat) {
        this.coMat = coMat;
    }

    public void setVang(int vang) {
        this.vang = vang;
    }

    public void setCoPhep(int coPhep) {
        this.coPhep = coPhep;
    }
}
