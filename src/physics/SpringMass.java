package physics;

import com.sun.javafx.geom.Vec3f;

public class SpringMass {
    private float Ks = 50f;
    private float Kd= 20f;
    private float rest_length = 0.1f;
    public PointMass mass;
    public PointMass startmass;
    private Vec3f start = new Vec3f(0f,0f,0f);


    public SpringMass(PointMass startmass, PointMass mass) {
        this.mass = mass;
        this.startmass = startmass;
    }

    public void giveForce(){
        Vec3f sub = new Vec3f(-this.startmass.position.x,-this.startmass.position.y,-this.startmass.position.z);
        sub.add(this.mass.position);

        Vec3f subV = new Vec3f(-this.startmass.velocity.x,-this.startmass.velocity.y,-this.startmass.velocity.z);
        subV.add(this.mass.velocity);

        float a = this.Ks*( (PointMass.length(sub)/rest_length)-1f)/PointMass.length(sub);
        float b = this.Kd* subV.dot(sub)/(rest_length*PointMass.length(sub)*PointMass.length(sub));
//        if (PointMass.length(sub)<1e-10){
//            a=0f;b=0f;
//        }

        System.out.println(PointMass.length(sub));

        this.mass.addForce(PointMass.mult(sub,-a-b));
        this.startmass.addForce(PointMass.mult(sub,a+b));
    }

    public float getK() {
        return Kd;
    }

    public void setK(float k) {
        Ks = k;
    }

    public float getRest_length() {
        return rest_length;
    }

    public void setRest_length(float rest_length) {
        this.rest_length = rest_length;
    }

    public PointMass getMass() {
        return mass;
    }

    public PointMass getstartMass() {
        return startmass;
    }


    public void setMass(PointMass mass) {
        this.mass = mass;
    }

    public Vec3f getStart() {

//        setStart(startmass.position);
        return start;
    }

    public Vec3f Add(Vec3f v1,Vec3f v2){
        return new Vec3f(v1.x+v2.x,v1.y+v2.y,v1.z+v2.z);
    }


    public void setStart(Vec3f start) {
        this.start = start;
    }
}
