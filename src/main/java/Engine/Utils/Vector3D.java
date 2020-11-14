/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Utils;

/**
 *
 * @author Alexandre Voghel
 */
public class Vector3D {
    private float x;
    private float y;
    private float z;
    
    public Vector3D() {
        this.x = 0;
        this.y = 0;
        this.z = 0;  
    }
    
    public Vector3D(float vector) {
        this.x = vector;
        this.y = vector;
        this.z = vector;
    }
    
    public Vector3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vector3D add(float x, float y, float z) {
        return new Vector3D(this.x + x, this.y +y, this.z + z);
    }
    
    public Vector3D add(Vector3D other) {
        return this.add(other.x, other.y, other.z);
    } 
    
    public float distance(Vector3D other) {
        return (float) Math.sqrt(((x - other.x)*(x - other.x))+((y - other.y)*(y - other.y))+((z - other.z)*(z - other.z)));
    }
}
