package mandelbrotset;

import java.awt.Color;

public class Complex {

    double real;
    double imaginary;
    double absolutesquared;

    public Complex(double r, double i) {
        real = r;
        imaginary = i;
        absolutesquared = (Math.pow(r, 2) + Math.pow(i, 2));
    }

    public void display() {
        if (real != 0) {
            System.out.print(real);
            if (imaginary != 0) {
                System.out.println("+" + imaginary + "i");
            } else {
                System.out.println("");
            }
        } else if (imaginary != 0) {
            System.out.println(imaginary + "i");
        }
    }


    public Complex multbyScalar(double d) {
        double r = real * d;
        double i = imaginary * d;
        return new Complex(r, i);
    }

    public Complex add(Complex c) {
        double r = this.real + c.real;
        double i = this.imaginary + c.imaginary;
        return new Complex(r, i);
    }

    public Complex subtract(Complex c) {
        double r = this.real - c.real;
        double i = this.imaginary - c.imaginary;
        return new Complex(r, i);
    }

    public Complex multbyComplex(Complex c) {
        double r = (this.real * c.real) - (this.imaginary * c.imaginary);
        double i = (this.real * c.imaginary) + (this.imaginary * c.real);
        return new Complex(r, i);
    }

    public double multbyConjugate() {
        double d = (Math.pow(this.real, 2) + Math.pow(this.imaginary, 2));
        return d;
    }

    public Complex makeConjugate() {
        double r = this.real;
        double i = this.imaginary * -1;
        return new Complex(r, i);
    }

    public Complex dividebyComplex(Complex c) {
        Complex num = this.multbyComplex(c.makeConjugate());
        double den = c.multbyConjugate();
        Complex d = num.dividebyScalar(den);
        return d;
    }

    public Complex dividebyScalar(double d) {
        double r = real / d;
        double i = imaginary / d;
        return new Complex(r, i);
    }

    public Complex exp(Complex c) {
        return new Complex(0, 0);
    }

    public float isinMS() {
        Complex z = this;
        int n = 0;
        float smooth;
        while (n != MandelbrotSet.iterations) {
            z = (z.multbyComplex(z)).add(this);
            if (z.absolutesquared > 4) {
                //Calculation for the normalized iteration count to make smooth colouring.
                //Refer to: COLORING DYNAMICAL SYSTEMS IN THE COMPLEX PLANE
                //by Francisco Garcia, Angel Fernandez, Javier Barrallo, Luis Martin 
                //note: the below calculation only works for the Mandelbrot Set, not for the Julia Set
                //-but that is okay, because Mandelbrot is more beautiful :P
                smooth= (float) ((n + 1 - Math.log(Math.log(Math.sqrt(z.absolutesquared)))/Math.log(2))/MandelbrotSet.iterations);
                return smooth;
            }
            n++;
        }
        return 0.0f;
    }

}