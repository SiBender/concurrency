package net.bondarik.concurrency.hortsmann;

import java.math.BigInteger;

public final class Matrix {
    private final BigInteger[][] data;

    public Matrix() {
        this.data = new BigInteger[][]{new BigInteger[]{BigInteger.ONE, BigInteger.ONE},
                new BigInteger[]{BigInteger.ONE, BigInteger.ZERO}};
    }

    public Matrix(BigInteger[][] data) {
        this.data = data;
    }

    public Matrix(int i) {
        this();
    }

    public BigInteger[][] getData() {
        return data;
    }

    public Matrix multiply(Matrix b) {
        BigInteger[][] aData = data;
        BigInteger[][] bData = b.getData();

        BigInteger[][] result = new BigInteger[2][2];
        result[0][0] = aData[0][0].multiply(bData[0][0]).add(aData[0][1].multiply(bData[1][0]));
        result[0][1] = aData[0][0].multiply(bData[0][1]).add(aData[0][1].multiply(bData[1][1]));
        result[1][0] = aData[1][0].multiply(bData[0][0]).add(aData[0][1].multiply(bData[1][0]));
        result[1][1] = aData[1][0].multiply(bData[1][0]).add(aData[1][1].multiply(bData[1][1]));
        return new Matrix(result);
    }

    @Override
    public String toString() {
        return data[0][0].toString();
    }
}
