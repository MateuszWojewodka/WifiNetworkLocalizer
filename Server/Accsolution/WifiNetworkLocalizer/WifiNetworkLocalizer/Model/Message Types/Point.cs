using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WifiNetworkLocalizer.Model.Message_Types
{
    public class Point
    {
        public int X { get; set; }
        public int Y { get; set; }

        public static Point operator +(Point A, Point B)
        {
            return new Point
            {
                X = A.X + B.X,
                Y = A.Y + B.Y
            };
        }

        public static Point operator -(Point A, Point B)
        {
            return new Point
            {
                X = A.X - B.X,
                Y = A.Y - B.Y
            };
        }
    }
}
