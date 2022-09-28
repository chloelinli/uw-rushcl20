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

/* A simple TextField that only allows numerical input */

import React, {Component} from 'react';

interface GridSizePickerProps {
    value: string;                    // text to display in the text area
    onChange(newSize: number): void;  // called when a new size is picked
}

class GridSizePicker extends Component<GridSizePickerProps> {

    onInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        // Every event handler with JS can optionally take a single parameter that
        // is an "event" object - contains information about an event. For mouse clicks,
        // it'll tell you thinks like what x/y coordinates the click was at. For text
        // box updates, it'll tell you the new contents of the text box, like we're using
        // below.

        // save new value
        let temp = event.target.value;
        var num = 0;

        // if user wants to backspace to empty string, save 0
        if (temp === "") {
            num = 0;
        }

        // else if value is within range, save num
        else if (parseInt(temp) >= 0 && parseInt(temp) <= 100) {
            num = parseInt(temp);
        }

        // save num as new size and set; set so size will only be from 0-100 O_O
        const newSize: number = num;
        this.props.onChange(newSize); // Tell our parent component about the new size.
    };

    render() {

        return (
            <div id="grid-size-picker">
                <p> Enter a number or click the arrows from 0 to 100, inclusive </p>
                <label>
                    Grid Size:
                    <input
                        value={this.props.value}
                        onChange={this.onInputChange}
                        type="number"
                        min={0}
                        max={100}
                    />
                </label>
            </div>
        );
    }
}

export default GridSizePicker;
