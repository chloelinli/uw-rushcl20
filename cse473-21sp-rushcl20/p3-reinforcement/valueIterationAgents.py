# valueIterationAgents.py
# -----------------------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


# valueIterationAgents.py
# -----------------------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


import mdp, util

from learningAgents import ValueEstimationAgent
import collections

class ValueIterationAgent(ValueEstimationAgent):
    """
        * Please read learningAgents.py before reading this.*

        A ValueIterationAgent takes a Markov decision process
        (see mdp.py) on initialization and runs value iteration
        for a given number of iterations using the supplied
        discount factor.
    """
    def __init__(self, mdp, discount = 0.9, iterations = 100):
        """
          Your value iteration agent should take an mdp on
          construction, run the indicated number of iterations
          and then act according to the resulting policy.

          Some useful mdp methods you will use:
              mdp.getStates()
              mdp.getPossibleActions(state)
              mdp.getTransitionStatesAndProbs(state, action)
              mdp.getReward(state, action, nextState)
              mdp.isTerminal(state)
        """
        self.mdp = mdp
        self.discount = discount
        self.iterations = iterations
        self.values = util.Counter() # A Counter is a dict with default 0
        self.runValueIteration()

    def runValueIteration(self):
        # Write value iteration code here
        for i in range(self.iterations):
            tempVal = self.values.copy()
            states = self.mdp.getStates()

            for s in states:
                if not self.mdp.isTerminal(s):
                    actions = self.mdp.getPossibleActions(s)
                    val = max([self.getQValue(s, a) for a in actions])
                    tempVal[s] = val

            self.values = tempVal

    def getValue(self, state):
        """
          Return the value of the state (computed in __init__).
        """
        return self.values[state]


    def computeQValueFromValues(self, state, action):
        """
          Compute the Q-value of action in state from the
          value function stored in self.values.
        """
        val = 0
        transitions = self.mdp.getTransitionStatesAndProbs(state, action)

        for s, p in transitions:
            r = self.mdp.getReward(state, action, s)
            val += p * (r + self.discount * self.values[s])

        return val

    def computeActionFromValues(self, state):
        """
          The policy is the best action in the given state
          according to the values currently stored in self.values.

          You may break ties any way you see fit.  Note that if
          there are no legal actions, which is the case at the
          terminal state, you should return None.
        """
        if self.mdp.isTerminal(state):
            return None

        actions = self.mdp.getPossibleActions(state)
        bestA, bestV = None, float("-inf")

        for a in actions:
            if self.mdp.isTerminal(a):
                return a

            val = self.computeQValueFromValues(state, a)
            if val > bestV:
                bestA, bestV = a, val

        return bestA

    def getPolicy(self, state):
        return self.computeActionFromValues(state)

    def getAction(self, state):
        "Returns the policy at the state (no exploration)."
        return self.computeActionFromValues(state)

    def getQValue(self, state, action):
        return self.computeQValueFromValues(state, action)

class AsynchronousValueIterationAgent(ValueIterationAgent):
    """
        * Please read learningAgents.py before reading this.*

        An AsynchronousValueIterationAgent takes a Markov decision process
        (see mdp.py) on initialization and runs cyclic value iteration
        for a given number of iterations using the supplied
        discount factor.
    """
    def __init__(self, mdp, discount = 0.9, iterations = 1000):
        """
          Your cyclic value iteration agent should take an mdp on
          construction, run the indicated number of iterations,
          and then act according to the resulting policy. Each iteration
          updates the value of only one state, which cycles through
          the states list. If the chosen state is terminal, nothing
          happens in that iteration.

          Some useful mdp methods you will use:
              mdp.getStates()
              mdp.getPossibleActions(state)
              mdp.getTransitionStatesAndProbs(state, action)
              mdp.getReward(state)
              mdp.isTerminal(state)
        """
        ValueIterationAgent.__init__(self, mdp, discount, iterations)

    def runValueIteration(self):
        states = self.mdp.getStates()
        for i in range(self.iterations):
            tempVal = self.values.copy()
            s = states[i % len(states)]

            if not self.mdp.isTerminal(s):
                actions = self.mdp.getPossibleActions(s)
                val = max([self.getQValue(s, a) for a in actions])
                tempVal[s] = val

            self.values = tempVal

class PrioritizedSweepingValueIterationAgent(AsynchronousValueIterationAgent):
    """
        * Please read learningAgents.py before reading this.*

        A PrioritizedSweepingValueIterationAgent takes a Markov decision process
        (see mdp.py) on initialization and runs prioritized sweeping value iteration
        for a given number of iterations using the supplied parameters.
    """
    def __init__(self, mdp, discount = 0.9, iterations = 100, theta = 1e-5):
        """
          Your prioritized sweeping value iteration agent should take an mdp on
          construction, run the indicated number of iterations,
          and then act according to the resulting policy.
        """
        self.theta = theta
        ValueIterationAgent.__init__(self, mdp, discount, iterations)

    def runValueIteration(self):
        predecessors = dict()
        states = self.mdp.getStates()

        for s in states:
            predecessors[s] = set()

        for s in states:
            if not self.mdp.isTerminal(s):
                actions = self.mdp.getPossibleActions(s)
                for a in actions:
                    nextStates = self.mdp.getTransitionStatesAndProbs(s, a)
                    for n, p in nextStates:
                        if n in predecessors:
                            predecessors[n].add(s)
                        else:
                            temp = set()
                            temp.add(s)
                            predecessors[n] = temp

        pq = util.PriorityQueue()

        for s in states:
            if not self.mdp.isTerminal(s):
                actions = self.mdp.getPossibleActions(s)
                sVal = self.values[s]
                qVal = max([self.getQValue(s, a) for a in actions])
                diff = abs(sVal-qVal)
                pq.update(s, -diff)

        for i in range(self.iterations):
            tempVal = self.values.copy()
            if pq.isEmpty():
                return

            s = pq.pop()
            if not self.mdp.isTerminal(s):
                actions = self.mdp.getPossibleActions(s)
                tempVal[s] = max([self.getQValue(s, a) for a in actions])
                self.values = tempVal

                pred = predecessors[s]

                if len(pred) > 0:
                    for p in pred:
                        actions = self.mdp.getPossibleActions(p)
                        pVal = self.values[p]
                        qVal = max([self.getQValue(p, a) for a in actions])
                        diff = abs(pVal-qVal)

                        if diff > self.theta:
                            pq.update(p, -diff)
