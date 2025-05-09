public class Classification {
    
    private int iddewey;
    private String nomclass;

    public Classification(int iddewey, String nomClass){
        this.iddewey = iddewey;
        this.nomclass = nomClass;
    }

    public int getIddewey() {
        return this.iddewey;
    }

    public String getNomclass() {
        return this.nomclass;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null){
            return false;
        }
        if(!(obj instanceof Classification)){
            return false;
        }
        Classification tmp = (Classification) obj;
        return this.iddewey == tmp.iddewey && this.nomclass.equals(tmp.nomclass);
    }
    
    @Override
    public String toString(){
        return "La classification " + this.iddewey + " a comme genre " + this.nomclass;
    }
}