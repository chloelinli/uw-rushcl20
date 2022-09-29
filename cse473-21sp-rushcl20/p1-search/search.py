# search.py
# ---------
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


"""
In search.py, you will implement generic search algorithms which are called by
Pacman agents (in searchAgents.py).
"""

import util

class SearchProblem:
    """
    This class outlines the structure of a search problem, but doesn't implement
    any of the methods (in object-oriented terminology: an abstract class).

    You do not need to change anything in this class, ever.
    """

    def getStartState(self):
        """
        Returns the start state for the search problem.
        """
        util.raiseNotDefined()

    def isGoalState(self, state):
        """
          state: Search state

        Returns True if and only if the state is a valid goal state.
        """
        util.raiseNotDefined()

    def getSuccessors(self, state):
        """
          state: Search state

        For a given state, this should return a list of triples, (successor,
        action, stepCost), where 'successor' is a successor to the current
        state, 'action' is the action required to get there, and 'stepCost' is
        the incremental cost of expanding to that successor.
        """
        util.raiseNotDefined()

    def getCostOfActions(self, actions):
        """
         actions: A list of actions to take

        This method returns the total cost of a particular sequence of actions.
        The sequence must be composed of legal moves.
        """
        util.raiseNotDefined()


def tinyMazeSearch(problem):
    """
    Returns a sequence of moves that solves tinyMaze.  For any other maze, the
    sequence of moves will be incorrect, so only use this for tinyMaze.
    """
    from game import Directions
    s = Directions.SOUTH
    w = Directions.WEST
    return  [s, s, w, s, w, w, s, w]

def depthFirstSearch(problem):
    """
    Search the deepest nodes in the search tree first.

    Your search algorithm needs to return a list of actions that reaches the
    goal. Make sure to implement a graph search algorithm.

    To get started, you might want to try some of these simple commands to
    understand the search problem that is being passed in:
    """

    """
    print("Start:", problem.getStartState())
    print("Is the start a goal?", problem.isGoalState(problem.getStartState()))
    print("Start's successors:", problem.getSuccessors(problem.getStartState()))
    """

    "*** YOUR CODE HERE ***"
    # initialize fringe, visited nodes, path, current node
    stack = util.Stack()
    visited = []
    path = []
    currNode = problem.getStartState()
    if problem.isGoalState(currNode):
        return currNode
    stack.push((currNode, path))

    # loop until return solution
    while True:
        # if no solution return fail
        if stack.isEmpty():
            return None

        # get new current node and path, add to visited
        currNode, path = stack.pop()

        if currNode not in visited:
            visited.append(currNode)

            # if at goal state return path, else continue to search
            if problem.isGoalState(currNode):
                # print(len(path))
                return path

            # get successors; if has, then if not visited add to path
            succ = problem.getSuccessors(currNode)
            for i in succ:
                if i[0] not in visited:
                    p = path + [i[1]]
                    stack.push((i[0], p))


def breadthFirstSearch(problem):
    """Search the shallowest nodes in the search tree first."""
    "*** YOUR CODE HERE ***"
    # initialize tree, visited, path, current node
    queue = util.Queue()
    visited = []
    path = []
    currNode = problem.getStartState()
    if problem.isGoalState(currNode):
        return currNode
    queue.push((currNode, path))

    # loop until return solution
    while True:
        # if no solution return fail
        if queue.isEmpty():
            return None

        # get new current node and path, add to visited
        currNode, path = queue.pop()

        if currNode not in visited:
            visited.append(currNode)

            # if at goal state return path, else continue to search
            if problem.isGoalState(currNode):
                return path

            # get successors; if has, then if not visited add to path
            succ = problem.getSuccessors(currNode)
            for i in succ:
                if i[0] not in visited:
                    p = path + [i[1]]
                    queue.push((i[0], p))

def uniformCostSearch(problem):
    """Search the node of least total cost first."""
    "*** YOUR CODE HERE ***"
    # initialize tree, visited, path, current node
    priority = util.PriorityQueue()
    visited = []
    path = []
    currNode = problem.getStartState()
    if problem.isGoalState(currNode):
        return currNode
    priority.push((currNode, path), 0)

    # loop until return solution
    while True:
        # if no solution return fail
        if priority.isEmpty():
            return None

        # get new current node and path, add to visited
        currState, path = priority.pop()

        # if at goal state return path, else continue to search
        if problem.isGoalState(currState):
            return path

        if currState not in visited:
            visited.append(currState)

            # get successors; if has, then if not visited get cost and add to path
            succ = problem.getSuccessors(currState)
            for i in succ:
                if i[0] not in visited:
                    p = path + [i[1]]
                    cost = problem.getCostOfActions(p)
                    priority.push((i[0], p), cost)

def nullHeuristic(state, problem=None):
    """
    A heuristic function estimates the cost from the current state to the nearest
    goal in the provided SearchProblem.  This heuristic is trivial.
    """
    return 0

def aStarSearch(problem, heuristic=nullHeuristic):
    """Search the node that has the lowest combined cost and heuristic first."""
    "*** YOUR CODE HERE ***"
    priority = util.PriorityQueue()
    visited = []
    path = []
    currNode = problem.getStartState()
    if problem.isGoalState(currNode):
        return currNode
    priority.push((currNode, path), 0)

    while True:
        if priority.isEmpty():
            return None

        currNode, path = priority.pop()

        if problem.isGoalState(currNode):
            return path

        if currNode not in visited:
            visited.append(currNode)

            succ = problem.getSuccessors(currNode)
            for i in succ:
                if i[0] not in visited:
                    p = path + [i[1]]
                    hcost = problem.getCostOfActions(p) + heuristic(i[0], problem)
                    priority.push((i[0], p), hcost)


# Abbreviations
bfs = breadthFirstSearch
dfs = depthFirstSearch
astar = aStarSearch
ucs = uniformCostSearch
