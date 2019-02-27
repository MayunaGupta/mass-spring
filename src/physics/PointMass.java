package physics;

import com.sun.javafx.geom.AreaOp;
import com.sun.javafx.geom.Vec3f;

public class PointMass {

    public float mass = 2f;
    public Vec3f position = new Vec3f(1f,0f,0f);
    public Vec3f velocity= new Vec3f(0f,0f,0f);
    public Vec3f force= new Vec3f(0f,0f,0f);
    public Vec3f gravity= new Vec3f(0f,-9.8f,0f);





    public Vec3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vec3f velocity) {
        this.velocity = velocity;
    }

    public Vec3f getForce() {
        return force;
    }

    public void setForce(Vec3f force) {
        this.force = force;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public Vec3f getPosition() {
        return position;
    }

    public void setPosition(Vec3f position) {
        this.position = position;
    }

    public Vec3f getGravity() {
        return gravity;
    }


    public void update(float dt) {
        calculateVelocity(dt);
        this.position.add(mult(this.velocity,dt));
    }

    public Vec3f mult(Vec3f V, float f){
        Vec3f v = new Vec3f(V.x,V.y,V.z);
        v.x = f*v.x;
        v.y= f*v.y;
        v.z = f*v.z;
        return v;
    }

    public void CalculateForces(){
        //Vec3f Totalforce = new Vec3f();
        Vec3f gravon= mult(this.gravity,this.mass);
        this.force.add(gravon);
        //Totalforce.add(force)
    }

     public float length(Vec3f vec){
         System.out.print("a ");

         float dist = (float) Math.sqrt(Math.pow(vec.x,2)+ Math.pow(vec.y,2)+ Math.pow(vec.z,2));
         System.out.println(dist);
        return (float) dist;
    }



    public void calculateVelocity(float dt){

        CalculateForces();
        Vec3f change = mult(this.force,dt/this.mass);
        this.velocity.add(change);

    }


//    public class mul extends Vec3f {
//        public   mul(float s) {
//            this.x =this.x * s;
//            this.y =this.y * s;
//            this.z = this.z * z;
//        }
//    }
}
