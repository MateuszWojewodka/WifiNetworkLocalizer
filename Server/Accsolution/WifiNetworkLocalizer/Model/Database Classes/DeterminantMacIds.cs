using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WifiNetworkLocalizer.Model.Message_Types
{
    [Table("DeterminantMacIds")]
    public class DeterminantMacIds
    {
        [Key]
        public int Id { get; set; }
        public string FirstMacId { get; set; }
        public string SecondMacId { get; set; }
        public string ThirdMacId { get; set; }
        public string RoomName { get; set; }
    }
}
