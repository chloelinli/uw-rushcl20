### Written Answers: 5/6

- testBaseCase failed for the same reason that testThrowsIllegalArgumentException
did, but for no other reason.  The fix identified for testBaseCase belongs as a
fix for testInductiveCase instead.

### Code Quality: 2/3

### Mechanics: 3/3

### General Feedback

- Good work overall!

### Specific Feedback

- Missing documentation for the new fields in `BallContainer.java` and/or `Box.java`.
Make sure to document new additions in the future!

- Your BallContainer add/remove methods are more complicated than they need to be.
Look at the documentation for `Set.add` and `Set.remove` and see whether you
need to explicitly handle cases the cases of adding something that already
exists in the set and removing something that doesn't exist in the set.

- When implementing `getBallsFromSmallest`, while `Comparable` is a great idiom,
it's often not a choice in realistic projects; often, you are not in control of
the source code for a class, or the object does not have a natural ordering, so
it would be stylistically incorrect to implement `Comparable`.  Even in the face
of this, you may be compelled to sort that object, and this is where
`Comparator` becomes helpful.  Try implementing `getBallsFromSmallest` again,
but this time leave the interface for `Ball` unchanged, and use a `Comparator`
to define an ordering instead.
