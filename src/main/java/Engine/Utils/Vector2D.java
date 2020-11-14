/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Utils;

/**
 *
 * @author bob
 */
public class Vector2D {

    public static final Vector2D ONE = new Vector2D(1, 1);
    public static final Vector2D ZERO = new Vector2D();

    public float x;
    public float y;

    public Vector2D() {
        x = 0;
        y = 0;
    }

    public Vector2D(float vector) {
        x = vector;
        y = vector;
    }

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D vector) {
        this.x = vector.x;
        this.y = vector.y;
    }

    public Vector2D Add(Vector2D other) {
        return new Vector2D(x + other.x, y + other.y);
    }

    public Vector2D Add(float vector) {
        return new Vector2D(x + vector, y + vector);
    }

    public Vector2D Subtract(Vector2D other) {
        return new Vector2D(x - other.x, y - other.y);
    }

    public Vector2D Subtract(float vector) {
        return new Vector2D(x - vector, y - vector);
    }

    public Vector2D Multiply(Vector2D other) {
        return new Vector2D(x * other.x, y * other.y);
    }

    public Vector2D Multiply(float vector) {
        return new Vector2D(x * vector, y * vector);
    }

    public Vector2D Divide(Vector2D other) {
        return new Vector2D(x / other.x, y / other.y);
    }

    public Vector2D Divide(float vector) {
        return new Vector2D(x / vector, y / vector);
    }

    public static Vector2D Divide(Vector2D first, Vector2D second) {
        Vector2D tempVector = new Vector2D();
        tempVector.x = first.x / second.x;
        tempVector.y = first.y / second.y;
        return tempVector;
    }

    public float Distance(Vector2D other) {
        return (float) Math.sqrt((other.x - this.x) * (other.x - this.x) + (other.y - this.y) * (other.y - this.y));
    }

    public Vector2D Inverse() {
        return this.Multiply(-1);
    }

    public Vector2D Copy() {
        return new Vector2D(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vector2D other = (Vector2D) obj;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Float.floatToIntBits(this.x);
        hash = 53 * hash + Float.floatToIntBits(this.y);
        return hash;
    }

    @Override
    public String toString() {
        return "Vector2D(" + this.x + ", " + this.y + ")";
    }
}
