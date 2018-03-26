﻿using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WifiNetworkLocalizer.Model.Message_Types
{
    public class ThreeMacIds
    {
        [Key]
        public int Id { get; set; }
        public string FirstMacId { get; set; }
        public string SecondMacId { get; set; }
        public string ThirdMacId { get; set; }
    }
}
