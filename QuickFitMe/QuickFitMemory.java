import java.util.*;

public class QuickFitMemory {
    
    // MemoryBlock class to represent a block of memory
    static class MemoryBlock {
        int size;
        String name;

        MemoryBlock(int size, String name) {
            this.size = size;
            this.name = name;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize memory blocks by size-specific lists
        LinkedList<MemoryBlock> thirtyKBList = new LinkedList<>(Arrays.asList(new MemoryBlock(30, "Block 1"), new MemoryBlock(30, "Block 2")));
        LinkedList<MemoryBlock> seventyKBList = new LinkedList<>(Arrays.asList(new MemoryBlock(70, "Block 3"), new MemoryBlock(70, "Block 4")));
        LinkedList<MemoryBlock> oneFiftyKBList = new LinkedList<>(Arrays.asList(new MemoryBlock(150, "Block 5"), new MemoryBlock(150, "Block 6")));
        List<MemoryBlock> freeBlocks = new ArrayList<>();
        
        // Input number of processes
        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Processes to allocate
        Map<String, Integer> processes = new LinkedHashMap<>();
        for (int i = 0; i < numProcesses; i++) {
            System.out.print("Enter process name: ");
            String processName = scanner.nextLine();
            System.out.print("Enter required memory size (KB): ");
            int requiredSize = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            processes.put(processName, requiredSize);
        }

        // Allocated blocks and free blocks
        Map<String, String> allocatedBlocks = new LinkedHashMap<>();
        List<MemoryBlock> smallBlocks = new ArrayList<>();

        // Process allocation loop
        for (Map.Entry<String, Integer> process : processes.entrySet()) {
            String processName = process.getKey();
            int requiredSize = process.getValue();

            MemoryBlock allocated = null;

            // Try to allocate based on exact size matches
            if (requiredSize == 30 && !thirtyKBList.isEmpty()) {
                allocated = thirtyKBList.removeFirst();
            } else if (requiredSize == 70 && !seventyKBList.isEmpty()) {
                allocated = seventyKBList.removeFirst();
            } else if (requiredSize == 150 && !oneFiftyKBList.isEmpty()) {
                allocated = oneFiftyKBList.removeFirst();
            }

            // If an allocation is made, record it
            if (allocated != null) {
                allocatedBlocks.put(processName, allocated.name + " (" + allocated.size + " KB)");
            } else {
                System.out.println("No matching block available for process: " + processName);
            }
        }

        // Collect remaining free blocks from all lists
        freeBlocks.addAll(thirtyKBList);
        freeBlocks.addAll(seventyKBList);
        freeBlocks.addAll(oneFiftyKBList);
        freeBlocks.addAll(smallBlocks);

        // Print final memory state
        System.out.println("\nAllocated Blocks:");
        for (Map.Entry<String, String> entry : allocatedBlocks.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("\nFree Blocks:");
        for (MemoryBlock block : freeBlocks) {
            System.out.println(block.name + ": " + block.size + " KB");
        }

        scanner.close();
    }
}
