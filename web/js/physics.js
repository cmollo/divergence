var physics = {
  isColliding: function(x1,y1,w1,h1,x2,y2,w2,h2){
    var topLeft1 = [x1,y1];
    var bottomRight1 = [x1+w1,y1+h1];
    var topLeft2 = [x2,y2];
    var bottomRight2 = [x2+w2,y2+h2];

    return (
      topLeft1[1] < bottomRight2[1] &&
      topLeft2[1] < bottomRight1[1] &&
      topLeft1[0] < bottomRight2[0] &&
      topLeft2[0] < bottomRight1[0]
    )
  }
}
