using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Model.Entity_Classes
{
    public class RSSIMeasurmentPoint
    {
        [Key]
        public int Id { get; set; }
        public string FirstMacIdRSSI { get; set; }
        public string SecondMacIdRSSI { get; set; }
        public string ThirdMacIdRSSI { get; set; }
        public int X { get; set; }
        public int Y { get; set; }
    }
}
