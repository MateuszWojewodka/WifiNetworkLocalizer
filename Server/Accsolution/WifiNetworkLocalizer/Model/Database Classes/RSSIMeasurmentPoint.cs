using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WifiNetworkLocalizer.Model.Message_Types;

namespace Model.Entity_Classes
{
    [Table("MeasurmentPoints")]
    public class RSSIMeasurmentPoint
    {
        [Key]
        public int Id { get; set; }
        public string FirstMacIdRSSI { get; set; }
        public string SecondMacIdRSSI { get; set; }
        public string ThirdMacIdRSSI { get; set; }
        public int X { get; set; }
        public int Y { get; set; }
        [ForeignKey("DeterminantMacIds")]
        public int RefId { get; set; }
        public DeterminantMacIds DeterminantMacIds { get; set; }
    }
}
