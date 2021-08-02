// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.help;

import java.util.Comparator;

public class HelpTopicComparator implements Comparator<HelpTopic>
{
    private static final TopicNameComparator tnc;
    private static final HelpTopicComparator htc;
    
    static {
        tnc = new TopicNameComparator(null);
        htc = new HelpTopicComparator();
    }
    
    public static TopicNameComparator topicNameComparatorInstance() {
        return HelpTopicComparator.tnc;
    }
    
    public static HelpTopicComparator helpTopicComparatorInstance() {
        return HelpTopicComparator.htc;
    }
    
    private HelpTopicComparator() {
    }
    
    @Override
    public int compare(final HelpTopic lhs, final HelpTopic rhs) {
        return HelpTopicComparator.tnc.compare(lhs.getName(), rhs.getName());
    }
    
    public static class TopicNameComparator implements Comparator<String>
    {
        private TopicNameComparator() {
        }
        
        @Override
        public int compare(final String lhs, final String rhs) {
            final boolean lhsStartSlash = lhs.startsWith("/");
            final boolean rhsStartSlash = rhs.startsWith("/");
            if (lhsStartSlash && !rhsStartSlash) {
                return 1;
            }
            if (!lhsStartSlash && rhsStartSlash) {
                return -1;
            }
            return lhs.compareToIgnoreCase(rhs);
        }
    }
}
