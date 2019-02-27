package physics;

public class Spring {

    private float K = 100f;
    private float rest = 0.75f;

    public float getK() {
        return K;
    }

    public void setK(float k) {
        K = k;
    }

    public float getRest() {
        return rest;
    }

    public void setRest(float rest) {
        this.rest = rest;
    }


    public void giveForce(PointMass m){

        float a = -K * (m.length(m.position)-rest)/m.length(m.position);

        m.setForce( m.mult(m.position,a) );

    }



    //PointMass.setForce(new Vec3f(point.position.x*a, point.position.y*a, point.position.z*a));


}
