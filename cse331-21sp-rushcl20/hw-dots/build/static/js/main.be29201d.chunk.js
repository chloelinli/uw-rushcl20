(this["webpackJsonphw-dots"]=this["webpackJsonphw-dots"]||[]).push([[0],{14:function(e,t,n){},15:function(e,t,n){"use strict";n.r(t);var r=n(1),a=n.n(r),i=n(7),c=n.n(i),o=n(2),s=n(3),l=n(5),u=n(4),d=n(0),h=function(e){Object(l.a)(n,e);var t=Object(u.a)(n);function n(){return Object(o.a)(this,n),t.apply(this,arguments)}return Object(s.a)(n,[{key:"render",value:function(){return Object(d.jsxs)("div",{id:"edge-list",children:["Edges ",Object(d.jsx)("br",{}),Object(d.jsx)("textarea",{rows:5,cols:30,onChange:function(){console.log("textarea onChange was called")},value:"I'm stuck..."})," ",Object(d.jsx)("br",{}),Object(d.jsx)("button",{onClick:function(){console.log("Draw onClick was called")},children:"Draw"}),Object(d.jsx)("button",{onClick:function(){console.log("Clear onClick was called")},children:"Clear"})]})}}]),n}(r.Component),p=n(8),j=function(e){Object(l.a)(n,e);var t=Object(u.a)(n);function n(e){var r;return Object(o.a)(this,n),(r=t.call(this,e)).canvasReference=void 0,r.redraw=function(){if(null===r.canvasReference.current)throw new Error("Unable to access canvas.");var e=r.canvasReference.current.getContext("2d");if(null===e)throw new Error("Unable to create canvas drawing context.");e.clearRect(0,0,r.props.width,r.props.height),null!==r.state.backgroundImage&&e.drawImage(r.state.backgroundImage,0,0);var t,n=r.getCoordinates(),a=Object(p.a)(n);try{for(a.s();!(t=a.n()).done;){var i=t.value;r.drawCircle(e,i)}}catch(c){a.e(c)}finally{a.f()}},r.getCoordinates=function(){var e=300/r.props.size;if(1===r.props.size)return[[250,250]];for(var t=new Array(r.props.size),n=0;n<r.props.size;n++)t[n]=new Array(r.props.size).map((function(e){return new Array(2)}));for(var a=0;a<r.props.size;a++)for(var i=0;i<r.props.size;i++)t[n][a]=[100+e*n,400+e*a];var c=Array(2).fill(0).map((function(e){return new Array(3).fill(1)}));return console.log(c),[[100,100],[100,200],[100,300],[100,400],[200,100],[200,200],[200,300],[200,400],[300,100],[300,200],[300,300],[300,400],[400,100],[400,200],[400,300],[400,400]]},r.drawCircle=function(e,t){e.fillStyle="white";var n=Math.min(4,100/r.props.size);e.beginPath(),e.arc(t[0],t[1],n,0,2*Math.PI),e.fill()},r.state={backgroundImage:null},r.canvasReference=a.a.createRef(),r}return Object(s.a)(n,[{key:"componentDidMount",value:function(){this.fetchAndSaveImage(),this.redraw()}},{key:"componentDidUpdate",value:function(){this.redraw()}},{key:"fetchAndSaveImage",value:function(){var e=this,t=new Image;t.onload=function(){e.setState({backgroundImage:t})},t.src="./image.jpg"}},{key:"render",value:function(){return Object(d.jsxs)("div",{id:"grid",children:[Object(d.jsx)("canvas",{ref:this.canvasReference,width:this.props.width,height:this.props.height}),Object(d.jsxs)("p",{children:["Current Grid Size: ",this.props.size]})]})}}]),n}(r.Component),f=function(e){Object(l.a)(n,e);var t=Object(u.a)(n);function n(){var e;Object(o.a)(this,n);for(var r=arguments.length,a=new Array(r),i=0;i<r;i++)a[i]=arguments[i];return(e=t.call.apply(t,[this].concat(a))).onInputChange=function(t){var n=parseInt(t.target.value);e.props.onChange(n)},e}return Object(s.a)(n,[{key:"render",value:function(){return Object(d.jsx)("div",{id:"grid-size-picker",children:Object(d.jsxs)("label",{children:["Grid Size:",Object(d.jsx)("input",{value:this.props.value,onChange:this.onInputChange,type:"number",min:1,max:100})]})})}}]),n}(r.Component),b=(n(14),function(e){Object(l.a)(n,e);var t=Object(u.a)(n);function n(e){var r;return Object(o.a)(this,n),(r=t.call(this,e)).updateGridSize=function(e){r.setState({gridSize:e})},r.state={gridSize:4},r}return Object(s.a)(n,[{key:"render",value:function(){return Object(d.jsxs)("div",{children:[Object(d.jsx)("p",{id:"app-title",children:"Connect the Dots!"}),Object(d.jsx)(f,{value:this.state.gridSize.toString(),onChange:this.updateGridSize}),Object(d.jsx)(j,{size:this.state.gridSize,width:500,height:500}),Object(d.jsx)(h,{onChange:function(e){console.log("EdgeList onChange",e)}})]})}}]),n}(r.Component));c.a.render(Object(d.jsx)(b,{}),document.getElementById("root"))}},[[15,1,2]]]);
//# sourceMappingURL=main.be29201d.chunk.js.map