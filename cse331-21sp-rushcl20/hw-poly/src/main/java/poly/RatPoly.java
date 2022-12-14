/*
 * Copyright (C) 2021 Kevin Zatloukal.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2021 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package poly;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * <b>RatPoly</b> represents an immutable single-variate polynomial expression. RatPolys are sums of
 * RatTerms with non-negative exponents.
 *
 * <p>Examples of RatPolys include "0", "x-10", and "x^3-2*x^2+5/3*x+3", and "NaN".
 */
// See RatNum's documentation for a definition of "immutable".
public final class RatPoly {

    /**
     * Holds all the RatTerms in this RatPoly.
     */
    private final List<RatTerm> terms;

    // Definitions:
    // For a RatPoly p, let C(p,i) be "p.terms.get(i).getCoeff()" and
    // E(p,i) be "p.terms.get(i).getExpt()"
    // length(p) be "p.terms.size()"
    // (These are helper functions that will make it easier for us
    // to write the remainder of the specifications. They are not
    // executable code; they just represent complex expressions in a
    // concise manner, so that we can stress the important parts of
    // other expressions in the spec rather than get bogged down in
    // the details of how we extract the coefficient for the 2nd term
    // or the exponent for the 5th term. So when you see C(p,i),
    // think "coefficient for the ith term in p".)
    //
    // Abstraction Function:
    // RatPoly, p, represents the polynomial equal to the sum of the
    // RatTerms contained in 'terms':
    // sum (0 <= i < length(p)): p.terms.get(i)
    // If there are no terms, then the RatPoly represents the zero
    // polynomial.
    //
    // Representation Invariant for every RatPoly p:
    // terms != null &&
    // forall i such that (0 <= i < length(p)), C(p,i) != 0 &&
    // forall i such that (0 <= i < length(p)), E(p,i) >= 0 &&
    // forall i such that (0 <= i < length(p) - 1), E(p,i) > E(p, i+1)
    // In other words:
    // * The terms field always points to some usable object.
    // * No term in a RatPoly has a zero coefficient.
    // * No term in a RatPoly has a negative exponent.
    // * The terms in a RatPoly are sorted in descending exponent order.
    // (It is implied that 'terms' does not contain any null elements by the
    // above
    // invariant.)

    /**
     * A constant holding a Not-a-Number (NaN) value of type RatPoly.
     */
    public static final RatPoly NaN = new RatPoly(RatTerm.NaN);

    /**
     * A constant holding a zero value of type RatPoly.
     */
    public static final RatPoly ZERO = new RatPoly();

    /**
     * @spec.effects Constructs a new Poly, "0".
     */
    public RatPoly() {
        terms = new ArrayList<RatTerm>();
        checkRep();
    }

    /**
     * @param rt the single term which the new RatPoly equals
     * @spec.requires {@code rt.getExpt() >= 0}
     * @spec.effects Constructs a new Poly equal to "rt". If rt.isZero(), constructs a "0" polynomial.
     */
    public RatPoly(RatTerm rt) {
        terms = new ArrayList<RatTerm>();

        if (!rt.isZero()) {
            terms.add(rt);
        }

        checkRep();
    }

    /**
     * @param c the constant in the term which the new RatPoly equals
     * @param e the exponent in the term which the new RatPoly equals
     * @spec.requires {@code e >= 0}
     * @spec.effects Constructs a new Poly equal to "c*x^e". If c is zero, constructs a "0"
     * polynomial.
     */
    public RatPoly(int c, int e) {
        terms = new ArrayList<RatTerm>();

        if (c != 0) {
            RatNum n = new RatNum(c);
            RatTerm t = new RatTerm(n, e);
            terms.add(t);
        }

        checkRep();
    }

    /**
     * @param rt a list of terms to be contained in the new RatPoly
     * @spec.requires 'rt' satisfies clauses given in rep. invariant
     * @spec.effects Constructs a new Poly using 'rt' as part of the representation. The method does
     * not make a copy of 'rt'.
     */
    private RatPoly(List<RatTerm> rt) {
        terms = rt;
        // The spec tells us that we don't need to make a copy of 'rt'
        checkRep();
    }

    /**
     * Returns the degree of this RatPoly.
     *
     * @return the largest exponent with a non-zero coefficient, or 0 if this is "0"
     * @spec.requires !this.isNaN()
     */
    public int degree() {
        if (this.terms.size() == 0) {
            return 0;
        }

        return this.terms.get(0).getExpt();
    }

    /**
     * Gets the RatTerm associated with degree 'deg'
     *
     * @param deg the degree for which to find the corresponding RatTerm
     * @return the RatTerm of degree 'deg'. If there is no term of degree 'deg' in this poly, then
     * returns the zero RatTerm.
     * @spec.requires !this.isNaN()
     */
    public RatTerm getTerm(int deg) {
        // {Inv: t = p_i where p_i is the ith term of p such that E(p, i) = deg}
        for (RatTerm t : this.terms) {
            if (t.getExpt() == deg) {
                return t;
            }
        }

        return RatTerm.ZERO;
    }


    /**
     * Returns true if this RatPoly is not-a-number.
     *
     * @return true if and only if this has some coefficient = "NaN"
     */
    public boolean isNaN() {
        // {Inv: t = p_i where p_i is the ith term of p such that C(p, i) != NaN}
        for (RatTerm t : terms) {
            if (t.isNaN()) {
                return true;
            }
        }

        return false;
    }


    /**
     * Inserts a term into a sorted sequence of terms, preserving the sorted nature of the sequence. 
     * If a term with the given degree already exists, adds their coefficients (helper procedure).
     *
     * <p>Definitions: Let a "Sorted List<RatTerm>" be a List<RatTerm> V such that [1] V is sorted in
     * descending exponent order && [2] there are no two RatTerms with the same exponent in V && [3]
     * there is no RatTerm in V with a coefficient equal to zero
     *
     * <p>For a Sorted List<RatTerm> V and integer e, let cofind(V, e) be either the coefficient for a
     * RatTerm rt in V whose exponent is e, or zero if there does not exist any such RatTerm in V.
     * (This is like the coeff function of RatPoly.) We will write sorted(lst) to denote that lst is a
     * Sorted List<RatTerm>, as defined above.
     *
     * @param lst     the list into which newTerm should be inserted
     * @param newTerm the term to be inserted into the list
     * @spec.requires lst != null && sorted(lst)
     * @spec.modifies lst
     * @spec.effects sorted(lst_post) && (cofind(lst_post,newTerm.getExpt()) =
     * cofind(lst,newTerm.getExpt()) + newTerm.getCoeff())
     */
    private static void sortedInsert(List<RatTerm> lst, RatTerm newTerm) {
        // in any case, only add newTerm if it is not 0 and has non-zero exponent

        if (lst.size() == 0 && !newTerm.isZero() && newTerm.getExpt() >= 0) {
            lst.add(newTerm);
        }

        // if !lst.isEmpty(), compare newTerm to every term in lst
        else {
            if (!newTerm.isZero() && newTerm.getExpt() >= 0) {
                for (int i = 0; i < lst.size(); i++) {
                    RatTerm t = lst.get(i);

                    // if newTerm degree > current degree add to current spot
                    if (newTerm.getExpt() > t.getExpt()) {
                        lst.add(i, newTerm);
                        return;
                    }

                    // else if like terms and has non-zero sum, replace
                    else if (t.getExpt() == newTerm.getExpt()) {
                        RatTerm new2 = t.add(newTerm);
                        lst.remove(i);
                        if (!new2.isZero()) {
                            lst.add(i, new2);
                        }

                        return;
                    }
                }

                lst.add(newTerm);
            }
        }
    }

    /**
     * Return the additive inverse of this RatPoly.
     *
     * @return a RatPoly equal to "0 - this"; if this.isNaN(), returns some r such that r.isNaN()
     */
    public RatPoly negate() {
        if (this.isNaN()) {
            return RatPoly.NaN;
        }

        List<RatTerm> temp = new ArrayList<RatTerm>();

        // {Inv: temp = (-p_0 - p_1 - ... - p_{i-1}) where i is ith term of p}
        for (RatTerm t : this.terms) {
            temp.add(t.negate());
        }

        return new RatPoly(temp);
    }

    /**
     * Addition operation.
     *
     * @param p the other value to be added
     * @return a RatPoly, r, such that r = "this + p"; if this.isNaN() or p.isNaN(), returns some r
     * such that r.isNaN()
     * @spec.requires p != null
     */
    public RatPoly add(RatPoly p) {
        if (this.isNaN() || p.isNaN()) {
            return RatPoly.NaN;
        }

        else if (this.equals(RatPoly.ZERO)) {
            return p;
        }

        else if (p.equals(RatPoly.ZERO)) {
            return this;
        }

        RatPoly r = new RatPoly(this.terms);

        // {Inv: r = q + p0 + p1 + ... + pi-1, where pi is the ith term in p}
        for (int i = 0; i < p.terms.size(); i++) {
            RatTerm pt = p.terms.get(i);
            int deg = pt.getExpt();
            RatTerm rt = r.getTerm(deg);

            // if has like term and sum is non-zero, replace
            if (!rt.isZero()) {
                int index = r.terms.indexOf(rt);
                r.terms.remove(index);
                RatTerm temp = pt.add(rt);

                if (!temp.isZero()) {
                    r.terms.add(index, temp);
                }
            }

            // else insert as new term
            else {
                sortedInsert(r.terms, pt);
            }
        }

        return new RatPoly(r.terms);
    }

    /**
     * Subtraction operation.
     *
     * @param p the value to be subtracted
     * @return a RatPoly, r, such that r = "this - p"; if this.isNaN() or p.isNaN(), returns some r
     * such that r.isNaN()
     * @spec.requires p != null
     */
    public RatPoly sub(RatPoly p) {
        return this.add(p.negate());
    }

    /**
     * Multiplication operation.
     *
     * @param p the other value to be multiplied
     * @return a RatPoly, r, such that r = "this * p"; if this.isNaN() or p.isNaN(), returns some r
     * such that r.isNaN()
     * @spec.requires p != null
     */
    public RatPoly mul(RatPoly p) {
        if (this.isNaN() || p.isNaN()) {
            return RatPoly.NaN;
        }

        else if (this.equals(RatPoly.ZERO) || p.equals(RatPoly.ZERO)) {
            return RatPoly.ZERO;
        }

        RatPoly r = new RatPoly();

        // {Inv_1: r = q * (p_0 + p_1 + ... + p_{i-1}) where p_i is ith term of p}
        for (RatTerm pt : p.terms) {
            // {Inv_2: r = (q_0 + q_1 + ... + q_{j-1}) * p_i where q_j is jth term of q}
            for (RatTerm qt : this.terms) {
                RatTerm temp = pt.mul(qt);

                r = r.add(new RatPoly(temp));
            }
        }

        return new RatPoly(r.terms);
    }

    /**
     * Truncating division operation.
     *
     * <p>Truncating division gives the number of whole times that the divisor is contained within the
     * dividend. That is, truncating division disregards or discards the remainder. Over the integers,
     * truncating division is sometimes called integer division; for example, 10/3=3, 15/2=7.
     *
     * <p>Here is a formal way to define truncating division: u/v = q, if there exists some r such
     * that:
     *
     * <ul>
     * <li>u = q * v + r<br>
     * <li>The degree of r is strictly less than the degree of v.
     * <li>The degree of q is no greater than the degree of u.
     * <li>r and q have no negative exponents.
     * </ul>
     * <p>
     * q is called the "quotient" and is the result of truncating division. r is called the
     * "remainder" and is discarded.
     *
     * <p>Here are examples of truncating division:
     *
     * <ul>
     * <li>"x^3-2*x+3" / "3*x^2" = "1/3*x" (with r = "-2*x+3")
     * <li>"x^2+2*x+15 / 2*x^3" = "0" (with r = "x^2+2*x+15")
     * <li>"x^3+x-1 / x+1 = x^2-x+2 (with r = "-3")
     * </ul>
     *
     * @param p the divisor
     * @return the result of truncating division, {@code this / p}. If p = 0 or this.isNaN() or
     * p.isNaN(), returns some q such that q.isNaN().
     * @spec.requires p != null
     */
    public RatPoly div(RatPoly p) {
        if (this.isNaN() || p.isNaN() || p.terms.size() == 0) {
            return RatPoly.NaN;
        }

        else if (p.terms.size() != 0 && p.terms.get(0).equals(new RatTerm(new RatNum(1),0))) {
            return this;
        }

        else if (this.equals(RatPoly.ZERO) || p.degree() > this.degree()) {
            return RatPoly.ZERO;
        }

        RatPoly r = new RatPoly();
        RatPoly s = new RatPoly(this.terms);

        // {Inv_1: r = (s_0 + s_1 + ... + s_{i-1}) / p where s_i is ith term of s}
        for (int i = 0; i < s.terms.size(); i++) {
            if (!s.equals(RatPoly.ZERO) && s.degree() < p.degree()) {
                break;
            }

            else {
                RatTerm temp = s.terms.get(i).div(p.terms.get(0));
                sortedInsert(r.terms, temp);
                RatPoly t = new RatPoly();

                // {Inv_2: t = temp val * (p_0 + p_1 + ... + p_{j-1}) where j is jth term of p}
                for (RatTerm pt : p.terms) {
                    RatTerm temp2 = temp.mul(pt);
                    if (!temp2.isZero() && temp.getExpt() >= 0) {
                        t.terms.add(temp2);
                    }

                    else {
                        i--;
                    }
                }

                if (!t.terms.isEmpty() && !s.terms.isEmpty()) {
                    int deg = s.terms.get(0).getExpt();
                    s = s.sub(t);

                    if (!s.terms.isEmpty() && s.terms.get(0).getExpt() != deg) {
                        i--;
                    }
                }
            }
        }

        return new RatPoly(r.terms);
    }

    /**
     * Return the derivative of this RatPoly.
     *
     * @return a RatPoly, q, such that q = dy/dx, where this == y. In other words, q is the derivative
     * of this. If this.isNaN(), then return some q such that q.isNaN().
     * <p>The derivative of a polynomial is the sum of the derivative of each term.
     */
    public RatPoly differentiate() {
        // if NaN, return Nan
        if (this.isNaN()) {
            return RatPoly.NaN;
        }

        // else find derivative of each term
        RatPoly q = new RatPoly();

        for (RatTerm t : this.terms) {
            RatTerm newTerm = t.differentiate();
            if (newTerm.getExpt() >= 0 && !newTerm.equals(RatTerm.ZERO)) {
                q.terms.add(newTerm);
            }
        }

        // return q
        return new RatPoly(q.terms);
    }

    /**
     * Returns the antiderivative of this RatPoly.
     *
     * @param integrationConstant the constant of integration to use when computing the antiderivative
     * @return a RatPoly, q, such that dq/dx = this and the constant of integration is
     * "integrationConstant" In other words, q is the antiderivative of this. If this.isNaN() or
     * integrationConstant.isNaN(), then return some q such that q.isNaN().
     * <p>The antiderivative of a polynomial is the sum of the antiderivative of each term plus
     * some constant.
     * @spec.requires integrationConstant != null
     */
    public RatPoly antiDifferentiate(RatNum integrationConstant) {
        // if either is NaN return NaN
        if (this.isNaN() || integrationConstant.isNaN()) {
            return RatPoly.NaN;
        }

        else if (this.equals(RatPoly.ZERO)) {
            return new RatPoly(new RatTerm(integrationConstant, 0));
        }

        // else find antiderivative of each term, then add constant at end
        RatPoly q = new RatPoly();

        for (RatTerm t : this.terms) {
            RatTerm newTerm = t.antiDifferentiate();
            if (newTerm.getExpt() >= 0 && !newTerm.isZero()) {
                q.terms.add(newTerm);
            }
        }

        if (!integrationConstant.equals(RatNum.ZERO)) {
            q.terms.add(new RatTerm(integrationConstant, 0));
        }

        return new RatPoly(q.terms);
    }

    /**
     * Returns the integral of this RatPoly, integrated from lowerBound to upperBound.
     *
     * <p>The Fundamental Theorem of Calculus states that the definite integral of f(x) with bounds a
     * to b is F(b) - F(a) where dF/dx = f(x) NOTE: Remember that the lowerBound can be higher than
     * the upperBound.
     *
     * @param lowerBound the lower bound of integration
     * @param upperBound the upper bound of integration
     * @return a double that is the definite integral of this with bounds of integration between
     * lowerBound and upperBound. If this.isNaN(), or either lowerBound or upperBound is
     * Double.NaN, return Double.NaN.
     */
    public double integrate(double lowerBound, double upperBound) {
        // if any NaN return NaN
        if (this.isNaN() || lowerBound == Double.NaN || upperBound == Double.NaN) {
            return Double.NaN;
        }

        // else integrate by bounds; if lower > upper, return negated value
        RatPoly f = this.antiDifferentiate(RatNum.ZERO);

        double val1 = f.eval(upperBound);
        double val2 = f.eval(lowerBound);
        return val1 - val2;
    }

    /**
     * Returns the value of this RatPoly, evaluated at d.
     *
     * @param d the value at which to evaluate this polynomial
     * @return the value of this polynomial when evaluated at 'd'. For example, "x+2" evaluated at 3
     * is 5, and "x^2-x" evaluated at 3 is 6. If (this.isNaN() == true), return Double.NaN.
     */
    public double eval(double d) {
        // if NaN return NaN
        if (this.isNaN()) {
            return Double.NaN;
        }

        // else evaluate
        double val = 0.0;

        for (RatTerm t : this.terms) {
            val += t.eval(d);
        }

        return val;
    }

    /**
     * Returns a string representation of this RatPoly. Valid example outputs include
     * "x^17-3/2*x^2+1", "-x+1", "-1/2", and "0".
     *
     * @return a String representation of the expression represented by this, with the terms sorted in
     * order of degree from highest to lowest.
     * <p>There is no whitespace in the returned string.
     * <p>If the polynomial is itself zero, the returned string will just be "0".
     * <p>If this.isNaN(), then the returned string will be just "NaN".
     * <p>The string for a non-zero, non-NaN poly is in the form "(-)T(+|-)T(+|-)...", where "(-)"
     * refers to a possible minus sign, if needed, and "(+|-)" refers to either a plus or minus
     * sign. For each term, T takes the form "C*x^E" or "C*x" where {@code C > 0}, UNLESS: (1) the
     * exponent E is zero, in which case T takes the form "C", or (2) the coefficient C is one, in
     * which case T takes the form "x^E" or "x". In cases were both (1) and (2) apply, (1) is
     * used.
     */
    @Override
    public String toString() {
        if(terms.size() == 0) {
            return "0";
        }
        if(isNaN()) {
            return "NaN";
        }
        StringBuilder output = new StringBuilder();
        boolean isFirst = true;
        for(RatTerm rt : terms) {
            if(isFirst) {
                isFirst = false;
                output.append(rt.toString());
            } else {
                if(rt.getCoeff().isNegative()) {
                    output.append(rt.toString());
                } else {
                    output.append("+" + rt.toString());
                }
            }
        }
        return output.toString();
    }

    /**
     * Builds a new RatPoly, given a descriptive String.
     *
     * @param polyStr a string of the format described in the @spec.requires clause.
     * @return a RatPoly p such that p.toString() = polyStr
     * @spec.requires 'polyStr' is an instance of a string with no spaces that expresses a poly in the
     * form defined in the toString() method.
     * <p>Valid inputs include "0", "x-10", and "x^3-2*x^2+5/3*x+3", and "NaN".
     */
    public static RatPoly valueOf(String polyStr) {

        List<RatTerm> parsedTerms = new ArrayList<>();

        // First we decompose the polyStr into its component terms;
        // third arg orders "+" and "-" to be returned as tokens.
        StringTokenizer termStrings = new StringTokenizer(polyStr, "+-", true);

        boolean nextTermIsNegative = false;
        while(termStrings.hasMoreTokens()) {
            String termToken = termStrings.nextToken();

            if(termToken.equals("-")) {
                nextTermIsNegative = true;
            } else if(termToken.equals("+")) {
                nextTermIsNegative = false;
            } else {
                // Not "+" or "-"; must be a term
                RatTerm term = RatTerm.valueOf(termToken);

                // at this point, coeff and expt are initialized.
                // Need to fix coeff if it was preceeded by a '-'
                if(nextTermIsNegative) {
                    term = term.negate();
                }

                // accumulate terms of polynomial in 'parsedTerms'
                sortedInsert(parsedTerms, term);
            }
        }
        return new RatPoly(parsedTerms);
    }

    /**
     * Standard hashCode function.
     *
     * @return an int that all objects equal to this will also return
     */
    @Override
    public int hashCode() {
        // all instances that are NaN must return the same hashcode;
        if(this.isNaN()) {
            return 0;
        }
        return terms.hashCode();
    }

    /**
     * Standard equality operation.
     *
     * @param obj the object to be compared for equality
     * @return true if and only if 'obj' is an instance of a RatPoly and 'this' and 'obj' represent
     * the same rational polynomial. Note that all NaN RatPolys are equal.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof RatPoly) {
            RatPoly rp = (RatPoly) obj;

            // special case: check if both are NaN
            if(this.isNaN() && rp.isNaN()) {
                return true;
            } else {
                return terms.equals(rp.terms);
            }
        } else {
            return false;
        }
    }

    /**
     * Throws an exception if the representation invariant is violated.
     */
    private void checkRep() {
        assert (terms != null);

        for(int i = 0; i < terms.size(); i++) {
            assert (!terms.get(i).getCoeff().equals(new RatNum(0))) : "zero coefficient";
            assert (terms.get(i).getExpt() >= 0) : "negative exponent";

            if(i < terms.size() - 1)
                assert (terms.get(i + 1).getExpt() < terms.get(i).getExpt()) : "terms out of order";
        }
    }
}
