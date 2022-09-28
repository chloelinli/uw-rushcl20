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

import React, {Component} from 'react';

interface EdgeListProps {
    value: string;
    onChange(edges: string): void;  // called when a new edge list is ready
                                 // once you decide how you want to communicate the edges to the App, you should
                                 // change the type of edges so it isn't `any`
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps> {
    /**
     * saves edges in prop
     */
    edgeChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
        // save text
        const temp: string = event.target.value;
        this.props.onChange(temp);
    };

    /**
     * takes saved edges, turns into array, and draws edges
     */
    drawEdges = () => {
        const edges: any[] = this.createEdges();
        var c = document.getElementById("grid");
//         var ctx = c.getContext("2d");
    };

    /**
     * takes saved edges and creates array separating each element on each line
     */
    createEdges = (): any[] => {
        if (this.props.value.length === 0) {
            alert("did you forget to add edges? proper format: #,# #,# COLOR");
            return [];
        }

        else {
            // save edges
            let temp: string = this.props.value;
            // split at each line
            let lineSplit: string[] = temp.split('\n');

            // split at spaces and commas
            let split = [];
            for (let i = 0; i < lineSplit.length; i++) {
                // get each line
                let line = lineSplit[i];

                // split at spaces if any
                let x = [];
                if (line.indexOf(' ') > -1) {
                    let temp = line.split(' ');
                    for (let j = 0; j < temp.length; j++) {
                        x.push(temp[j]);
                    }
                }

                // split first two indices at commas, if any
                let y: number[] = [];
                let s1 = x[0];
                if (s1.includes(',')) {
                    let temp = s1.split(',');
                    for (let j = 0; j < temp.length; j++) {
                        if (Number.isInteger(parseInt(temp[j]))) {
                            y.push(parseInt(temp[j]));
                        }
                    }
                }
                let z: number[] = [];
                let s2 = "";
                if (x.length > 1) {
                    s2 = x[1];
                }
                if (s2.indexOf(',')> -1) {
                    let temp = s2.split(',');
                    for (let j = 0; j < temp.length; j++) {
                        if (Number.isInteger(parseInt(temp[j]))) {
                            z.push(parseInt(temp[j]));
                        }
                    }
                }

                let n = [];
                // for integer in comma-split indices, push into new array to save at index
                for (let j = 0; j < y.length; j++) {
                    n.push(y[j]);
                }
                for (let j = 0; j < y.length; j++) {
                    n.push(z[j]);
                }

                // push last word of line into array
                n.push(x[x.length-1]);
                // if length of array != 5, alert, else insert into split array index
                if (n.length !== 5 || x.length !== 3 || y.length !== 2 || z.length !== 2) {
                    alert("invalid input, please try again. proper format: X1,Y1 X2,Y2 COLOR");
                }
                split.push(n);
            }

            // return array
            return split;
        };
//
//         // save edges
//         let temp = this.props.value.toString();
//
//         // split at lines
//         const lineSplit = temp.split('\n');
//
//         // split at spaces and commas
//         var split = new Array(lineSplit.length);
//
//         for (var i = 0; i < lineSplit.length; i++) {
//             // grab lines
//             var line = lineSplit[i];
//
//             // split at spaces - if no space or length != 3, alert
//             var x: string[] = [];
//             if (line.includes(' ')) {
//                 x = line.split(' ');
//             }
//
//             // split at commas - if no commas or length of each separated != 2, alert
//             var y: number[] = [];
//             var z: number[] = [];
//             if (x.length === 3 && x[0].length === 2 && x[1].length === 2) {
//                 if (x[0].includes(',')) {
//                     var temp1 = x[0].split(',');
//                     for (var t1 = 0; t1 < temp1.length; t1++) {
//                         y.push(parseInt(temp1[t1]));
//                     }
//                 }
//                 if (x[1].includes(',')) {
//                     var temp2 = x[1].split(',');
//                     for (var t2 = 0; t2 < temp2.length; t2++) {
//                         z.push(parseInt(temp2[t2]));
//                     }
//                 }
//             }
//
//             // save all values
//             var n = [];
//             for (var a = 0; a < y.length; a++) {
//                 n.push(y[a]);
//             }
//             for (var b = 0; b < z.length; b++) {
//                 n.push(z[b]);
//             }
//             n.push(x[x.length-1]);
//             if (n.length !== 5) {
//                 alert("invalid input, please try again. proper format: X1,Y1 X2,Y2 COLOR");
//             }
//             split[i] = n;
//         }
//         return split;
    };

    render() {
        return (
            <div id="edge-list">
                <p> Enter edges on separate lines in format: X1,Y1 X2,Y2 COLOR </p>
                Edges <br/>
                <textarea
                    rows={5}
                    cols={30}
                    onChange={this.edgeChange}
                /> <br/>
                <button onClick={this.drawEdges}>Draw</button>
                <button onClick={() => {console.log('Clear onClick was called');}}>Clear</button>
            </div>
        );
    }
}

export default EdgeList;
