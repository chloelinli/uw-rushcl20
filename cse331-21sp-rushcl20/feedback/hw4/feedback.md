### Written Answers: 20/26

### Code Quality: 2/3

### Mechanics: 3/3

### General Feedback

Good Job! See below comments for specific feedback.

### Specific Feedback

- In part 2b, the changes required are limited to the following methods:
  checkRep, equals, toString, getExpt, and hashCode.

- In part 1b, The method specs has a @spec.modifies clause that specifies that
  RatNum is immutable.

- 1.3 - 2.1, Final immutable fields cannot be modified after they are
  instantiated; the compiler would complain about any attempt to do so.
  Therefore, we can reason that RatNum and RatTerm cannot contain any bugs with
  regard to the representation invariant as long as we ensure the coherency of
  the data at initialization. Therefore, RatNum and RatTerm are special cases
  that do not need calls to checkRep at the beginning and end of every public
  method, aside from the constructor.

- 3.1 - checkRep does not assume that methods were implemented properly,
  regardless of how trivial or innocuous they seem. So, in general, even
  observer methods need calls to checkRep at their beginning and end.

- There should be loop invariants on non-trivial loops in
  RatPoly.

- Missing calls to checkRep at the beginning and end of every public method 
in RatPoly and/or RatPolyStack.